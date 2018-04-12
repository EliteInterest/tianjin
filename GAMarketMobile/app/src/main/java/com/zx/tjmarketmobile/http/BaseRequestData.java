package com.zx.tjmarketmobile.http;

import android.accounts.NetworkErrorException;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.zx.tjmarketmobile.R;
import com.zx.tjmarketmobile.util.LogUtil;
import com.zx.tjmarketmobile.util.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

/**
 * @author zsx
 * @date 2013-8-9
 * @description
 * @param<Param> HttpClient 请求参数类型
 * @param<ParamType> loadData 参数类型
 * @param<Result> 返回参数
 */
@SuppressWarnings("deprecation")
public abstract class BaseRequestData<HttpClientParam, loadDataParam, Result extends BaseHttpResult> {
    /**
     * 默认所有请求可同时开5线程
     */
    protected static final ExecutorService HTTPEXCUTORS = Executors.newFixedThreadPool(5);
    protected Handler pHandler = new Handler();
    private int id;
    private String error_message;
    private Result pBean;
    private boolean b_isDownding = false;
    protected OnHttpLoadingListener<Result> listener;
    private final static String TAG = "BaseRequestData";

    public void setLoadingListener(OnHttpLoadingListener<Result> listener) {
        this.listener = listener;
    }

    public int getId() {
        return id;
    }

    public BaseRequestData(int id) {
        this.id = id;
    }

    public boolean isLoading() {
        return b_isDownding;
    }

    public interface OnHttpLoadingListener<Result> {
        /**
         * 开始下载
         *
         * @param id
         */
        void onLoadStart(int id);

        /**
         * 请求发生错误
         *
         * @param id
         * @param isAPIError    <ul>
         *                      <li>true parStr 解析错误</li>
         *                      <li>false 请求超时 网络连接异常等</li>
         *                      </ul>
         * @param error_message 错误消息
         */
        void onLoadError(int id, boolean isAPIError, String error_message);

        void onLoadComplete(int id, Result b);

        void onLoadPregress(int id, int progress);
    }

    @SafeVarargs
    protected final void _loadData(loadDataParam... objects) {
        requestData(objects);
    }

    public boolean hasCache() {
        return pBean != null;
    }

    public void clear() {
        pBean = null;
    }

    /**
     * @return 上一次加载的数据
     */
    public Result getLastData() {
        return pBean;
    }

    @SafeVarargs
    private synchronized final void requestData(loadDataParam... objects) {
        if (b_isDownding) {
            if (LogUtil.DEBUG) {
                LogUtil.e(this, "id:" + id + "\t 正在进行请求");
            }
//            return;
        }
        // if (Lib_BaseApplication._Current_NetWork_Status == NetType.NoneNet) {
        // if (LogUtil.DEBUG) {
        // LogUtil.e(this, "网络链接异常");
        // }
        // onRequestError(id, "网络链接异常");
        // return;
        // }
        b_isDownding = true;
        BaseHttpParams<HttpClientParam> pParams = getHttpParams(id, objects);
        HTTPEXCUTORS.execute(new HttpWork(pParams));
    }

    /**
     * 未做保护 可连续请求
     *
     * @param objects
     */
    @SafeVarargs
    protected final void queueLoadData(loadDataParam... objects) {
        BaseHttpParams<HttpClientParam> pParams = getHttpParams(id, objects);
        HTTPEXCUTORS.execute(new HttpWork(pParams));
    }

    private class HttpWork implements Runnable {
        private BaseHttpParams<HttpClientParam> params;

        public HttpWork(BaseHttpParams<HttpClientParam> params) {
            this.params = params;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void run() {
            String str = null;
            pHandler.post(new Runnable() {

                @Override
                public void run() {
                    onRequestStart(id);
                }
            });
            error_message = null;
            try {
                HttpClientParam param = params.getParams(id);
                switch (params.getRequestMothod()) {
                    case GET:
                        String getUrl = null;
                        if (param == null) {
                            getUrl = params.getRequestUrl(id);
                        } else if (param instanceof Map) {
                            Map<String, Object> m = (Map<String, Object>) param;
                            String getParam = null;
                            if (m.size() == 0) {
                                getParam = "";
                            } else {
                                getParam = "?";
                                for (String key : m.keySet()) {
                                    Object o = m.get(key);
                                    getParam += key + "=" + String.valueOf(o == null ? "" : o);
                                    getParam += "&";
                                }
                                getParam = getParam.substring(0, getParam.length() - 1);
                            }
                            getUrl = params.getRequestUrl(id) + getParam;
                        } else {
                            getUrl = params.getRequestUrl(id);
                        }
                        if (LogUtil.DEBUG) {
                            LogUtil.e("requestData params:", String.valueOf(getUrl));
                        }
                        getJson(getUrl, params.isRetry());
                        break;
                    case POST:
                        if (LogUtil.DEBUG) {
                            LogUtil.e("requestData params:", String.valueOf(param));
                        }
                        if (param instanceof Map) {
//                            if (id == ApiData.HTTP_ID_login)
                            postJsonRequest(params.getRequestUrl(id), (Map<String, Object>) param, params.isRetry());
//                            else
//                                postJson(params.getRequestUrl(id), (Map<String, Object>) param, params.isRetry());
                        } else {
                            postJson(params.getRequestUrl(id), param.toString(), params.isRetry());
                        }

//                        if (param instanceof Map) {
//                            postJson(params.getRequestUrl(id), (Map<String, Object>) param, params.isRetry());
//                        } else {
//                            postJson(params.getRequestUrl(id), param.toString(), params.isRetry());
//                        }
                        break;
                    default:
                        throw new IllegalArgumentException("没有此请求方法");
                }
            } catch (ClassCastException e) {
                LogUtil.e(e);
                error_message = "参数类型错误";
            } catch (URISyntaxException e) {
                LogUtil.w(e);
                error_message = "网络地址错误";
            } catch (TimeoutException e) {
                error_message = "连接超时";
                LogUtil.w(e);
            } catch (SocketTimeoutException e) {
                error_message = "请求超时";
                LogUtil.w(e);
            }
            // catch (ClientProtocolException e) {
            // error_message = "网络协议错误";
            // LogUtil.w(e);
//        }

            catch (NetworkErrorException e) {
                error_message = "服务器未响应";
                LogUtil.w(e);
            } catch (
                    IOException e
                    )

            {
                error_message = "网络连接失败";
                LogUtil.e(e);
            } catch (
                    Exception e
                    )

            {
                error_message = "网络连接失败";
                LogUtil.e(e);
            }
        }

    }

    public void postJson(String url, boolean isRetry) throws TimeoutException, IOException, NetworkErrorException, URISyntaxException {
        Map<String, Object> newparams = new HashMap<>();
        postJson(url, newparams, isRetry);
    }

    public void postJson(String url, String params, boolean isRetry) throws TimeoutException, IOException, NetworkErrorException, URISyntaxException {
        Map<String, Object> newparams = new HashMap<>();
        newparams.put(params, params);
        postJson(url, newparams, isRetry);
    }


//    private void processHttp(final String url, final JSONObject jsonObject) {
//        new Thread(new Runnable() {
//
//            @SuppressWarnings("deprecation")
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                // 服务器 ：服务器项目 ：servlet名称
//                try {
//                    Log.i("wangwansheng", "url is " + url);
//                    HttpPost httpPost = new HttpPost(url);
//                    String result = "";
//                    if (jsonObject != null) {
//                        Log.i("wangwansheng", "pwd is " + jsonObject.toString());
//                        httpPost.setEntity(new StringEntity(jsonObject.toString(), HTTP.UTF_8));
//                    } else {
//                        Log.i("wangwansheng", "jsonObject is NULL!");
//                    }
//
//                    httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
//
//                    DefaultHttpClient httpclient = new HttpClient();
//                    HttpResponse response = httpclient.execute(httpPost);
//                    Log.i("wangwansheng", "result is " + response.getStatusLine().getStatusCode());
//                    if (response.getStatusLine().getStatusCode() == 200) {
//                        HttpEntity entity = response.getEntity();
//                        result = EntityUtils.toString(entity, HTTP.UTF_8);
//                        Log.i("wangwansheng", "result is " + result);
//
//                        if (result == null) {
//                            pHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    onRequestError(id, "未请求到数据");
//                                }
//                            });
//                        } else {
//                            final String returnStr = result.replaceAll("\ufeff", "");
//                            // final String returnStr = str;
//                            Log.i(TAG, "returnStr is " + returnStr);
//                            pHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    onRequestComplete(id, pBean, returnStr);
//                                }
//                            });
//                        }
//
//                    } else {
//                    }
//
//                } catch (ClientProtocolException e) {
//                    // TODO: handle exception
//                } catch (IOException e) {
//                    // TODO: handle exception
//                }
//            }
//        }).start();
//    }

    /**
     * post方法
     *
     * @param url
     * @param params
     * @throws URISyntaxException
     * @throws SocketTimeoutException
     * @throws IOException
     * @throws TimeoutException
     */
    public void postJsonRequest(String url, final Map<String, Object> params, boolean isRetry) throws URISyntaxException, IOException, NetworkErrorException,
            TimeoutException, JSONException {
        JSONObject jsonObject = new JSONObject(params);

        SessionStoreRequest jsonObjectRequest = new SessionStoreRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                {
                    Log.e("result", jsonObject.toString());
                    String response = jsonObject.toString();
                    if (response == null) {
                        pHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onRequestError(id, "未请求到数据");
                            }
                        });
                    } else {
                        final String returnStr = response.replaceAll("\ufeff", "");
                        // final String returnStr = str;
                        Log.i(TAG, "returnStr is " + returnStr);
                        pHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onRequestComplete(id, pBean, returnStr);
                            }
                        });
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onRequestError(id, "网络请求失败");
                    }
                });
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                if (headers == null
                        || headers.equals(Collections.emptyMap())) {
                    headers = new HashMap<String, String>();
                }

                MyApplication.getAppContext().addSessionCookie(headers);
                return headers;
            }
        };
        if (isRetry) {
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(8 * 1000, 1, 1.0f));
        } else {
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 0, 1.0f));
        }
        jsonObjectRequest.setTag(url);


//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                {
//                    Log.e("result", jsonObject.toString());
//                    String response = jsonObject.toString();
//                    if (response == null) {
//                        pHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                onRequestError(id, "未请求到数据");
//                            }
//                        });
//                    } else {
//                        final String returnStr = response.replaceAll("\ufeff", "");
//                        // final String returnStr = str;
//                        Log.i(TAG, "returnStr is " + returnStr);
//                        pHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                onRequestComplete(id, pBean, returnStr);
//                            }
//                        });
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                int statusCode = volleyError.networkResponse.statusCode;
//                pHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        onRequestError(id, "网络请求失败");
//                    }
//                });
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Accept", "application/json");
//                headers.put("Content-Type", "application/json; charset=UTF-8");
//
//                return headers;
//            }
//        };
//        if (isRetry) {
//            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(8 * 1000, 1, 1.0f));
//        } else {
//            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 0, 1.0f));
//        }
//        jsonObjectRequest.setTag(url);
        MyApplication.getQueue().add(jsonObjectRequest);

    }

    /**
     * post方法
     *
     * @param url
     * @param params
     * @throws URISyntaxException
     * @throws SocketTimeoutException
     * @throws IOException
     * @throws TimeoutException
     */
    public void postJson(String url, final Map<String, Object> params, boolean isRetry) throws URISyntaxException, IOException, NetworkErrorException,
            TimeoutException {
        Log.i(TAG, "url is " + url);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("result", response);
                if (response == null) {
                    pHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onRequestError(id, "未请求到数据");
                        }
                    });
                } else {
                    final String returnStr = response.replaceAll("\ufeff", "");
                    // final String returnStr = str;
                    Log.i(TAG, "returnStr is " + returnStr);
                    pHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onRequestComplete(id, pBean, returnStr);
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                int statusCode = error.networkResponse.statusCode;
                pHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onRequestError(id, "网络请求失败");
                    }
                });
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> theParams = new HashMap<String, String>();
                for (String key : params.keySet()) {
                    if (params.get(key) == null) {
                        theParams.put(key, "");
                    } else {
                        theParams.put(key, params.get(key).toString());
                    }
                }
                return theParams;
            }
        };
        if (isRetry) {
            postRequest.setRetryPolicy(new DefaultRetryPolicy(8 * 1000, 1, 1.0f));
        } else {
            postRequest.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 0, 1.0f));
        }
        postRequest.setTag(url);
        MyApplication.getQueue().add(postRequest);
    }

    /**
     * get方法
     *
     * @param url
     * @throws URISyntaxException
     * @throws SocketTimeoutException
     * @throws IOException
     * @throws TimeoutException
     */
    public void getJson(String url, boolean isRetry) throws URISyntaxException, IOException,
            TimeoutException {
        PostStringRequest getRequest = new PostStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("result", response);
                if (response == null) {
                    pHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onRequestError(id, "null");
                        }
                    });
                } else {
                    final String returnStr = response.replaceAll("\ufeff", "");
                    // final String returnStr = str;
                    pHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onRequestComplete(id, pBean, returnStr);
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onRequestError(id, "网络请求失败");
                    }
                });
            }
        });
        if (isRetry) {
            getRequest.setRetryPolicy(new DefaultRetryPolicy(8 * 1000, 1, 1.0f));
        } else {
            getRequest.setRetryPolicy(new DefaultRetryPolicy(8 * 1000, 0, 1.0f));
        }

//        StringRequest getRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.e("result", response);
//                if (response == null) {
//                    pHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            onRequestError(id, "null");
//                        }
//                    });
//                } else {
//                    final String returnStr = response.replaceAll("\ufeff", "");
//                    // final String returnStr = str;
//                    pHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            onRequestComplete(id, pBean, returnStr);
//                        }
//                    });
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                pHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        onRequestError(id, "网络请求失败");
//                    }
//                });
//            }
//        });
//        if (isRetry) {
//            getRequest.setRetryPolicy(new DefaultRetryPolicy(8 * 1000, 1, 1.0f));
//        } else {
//            getRequest.setRetryPolicy(new DefaultRetryPolicy(8 * 1000, 0, 1.0f));
//        }
        getRequest.setTag(url);
        MyApplication.getQueue().add(getRequest);
    }

    private void onRequestStart(int id) {
        b_isDownding = true;
        if (listener != null) {
            listener.onLoadStart(id);
        } else {
            __onStart(id);
        }
    }

    private final void onRequestError(int id, String error_message) {
        b_isDownding = false;
        if (listener != null) {
            listener.onLoadError(id, false, error_message);
        } else {
            __onError(id, false, error_message);
        }
    }

    private final void onRequestComplete(int id, Result source, String returnStr) {
        try {
            pBean = parseStr(id, returnStr, source);
            b_isDownding = false;
            if (pBean.isSuccess()) {
                if (listener != null) {
                    listener.onLoadComplete(id, pBean);
                } else {
                    __onComplete(id, pBean);
                }
            } else {
                if (listener != null) {
                    listener.onLoadError(id, true, pBean.getMessage());
                } else {
                    __onError(id, true, pBean.getMessage());
                }
            }
        } catch (Exception e) {
            if (LogUtil.DEBUG) {
                LogUtil.e(e);
            }
            b_isDownding = false;
            // if (listener == null) {
            // __onError(id, false, "解析发生异常");
            // } else {
            // listener.onLoadError(id, false, "解析发生异常");
            // }
        }
    }

    /**
     * 构造请求参数
     *
     * @return
     * @paramparams
     * @paramobjects
     */
    protected abstract BaseHttpParams<HttpClientParam> getHttpParams(int id, loadDataParam... objects);

    /**
     * @param currentDownloadText Http请求的数据
     * @param lastData            上一次 parseStr()方法返回的数据
     * @return 会在onComplete()中回调出去
     * @throws Exception
     */
    protected abstract Result parseStr(int id, String currentDownloadText, Result lastData) throws Exception;

    /**
     * 开始下载
     *
     * @param id
     */
    protected void __onStart(int id) {
    }

    /**
     * 请求发生错误
     *
     * @param id
     * @param isAPIError    <ul>
     *                      <li>true parStr 解析错误</li>
     *                      <li>false 请求超时 网络连接异常等</li>
     *                      </ul>
     * @param error_message 错误消息
     */
    protected void __onError(int id, boolean isAPIError, String error_message) {
    }

    protected void __onComplete(int id, Result b) {
    }

    private ProgressDialog progressDialog;
    private String loadMessage = "正在加载数据，请稍后...";

    public final synchronized void showLoadingDialog(Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context, R.style.dialogTransparent);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage(loadMessage);
        }
        if (progressDialog.isShowing()) {
            return;
        }
        progressDialog.show();
    }

    public final synchronized void cancelLoadingDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.cancel();
                progressDialog = null;
            }
        }
    }

    public final synchronized void setLoadingDialogText(String loadMessage) {
        this.loadMessage = loadMessage;
        if (progressDialog != null) {
            progressDialog.setMessage(loadMessage);
        }
    }

    /**
     * 带取消按钮的diaolog
     *
     * @param context
     */
    public final synchronized void showLoadingDialogHasCancel(Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context, R.style.dialogTransparent);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("下载准备中...");
            progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cancelLoadingDialog();
                }
            });
        }
        if (progressDialog.isShowing()) {
            return;
        }
        progressDialog.show();
    }
}
