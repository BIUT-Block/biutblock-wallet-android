package com.hualianzb.biut.models;

import java.io.Serializable;
import java.util.List;

/**
 * Date:2018/12/1
 * auther:wangtianyun
 * describe:
 */
public class SendRawBean implements Serializable {
    /**
     * method : sec_sendRawTransaction
     * params : [{"timestamp":1557148654820,"from":"e27075290aeeffeb943ec5fd23fe834e4d4499e1","to":"a918f160a6b951991ce9290ce7be9b72a2b5c55b","value":"11","gasLimit":"0","gas":"0","gasPrice":"0","inputData":"","data":{"v":27,"r":"bf2ac04c25fbf955ce796be3e079043cb3fb576a57027a17b1ca11281d60751a","s":"728ea26b372e95bc32e937a00ebd8b2fa22e909989866aaf396722a0a4ef4c9a"},"txFee":"0.02"}]
     */

    private String method;
    private List<ParamsBean> params;
    private String jsonrpc;
    private int id;

    public SendRawBean() {
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<ParamsBean> getParams() {
        return params;
    }

    public void setParams(List<ParamsBean> params) {
        this.params = params;
    }

    public static class ParamsBean {
        /**
         * timestamp : 1557148654820
         * from : e27075290aeeffeb943ec5fd23fe834e4d4499e1
         * to : a918f160a6b951991ce9290ce7be9b72a2b5c55b
         * value : 11
         * gasLimit : 0
         * gas : 0
         * gasPrice : 0
         * inputData :
         * data : {"v":27,"r":"bf2ac04c25fbf955ce796be3e079043cb3fb576a57027a17b1ca11281d60751a","s":"728ea26b372e95bc32e937a00ebd8b2fa22e909989866aaf396722a0a4ef4c9a"}
         * txFee : 0.02
         */

        private long timestamp;
        private String from;
        private String to;
        private String value;
        private String gasLimit;
        private String gas;
        private String gasPrice;
        private String inputData;
        private DataBean data;
        private String txFee;

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getGasLimit() {
            return gasLimit;
        }

        public void setGasLimit(String gasLimit) {
            this.gasLimit = gasLimit;
        }

        public String getGas() {
            return gas;
        }

        public void setGas(String gas) {
            this.gas = gas;
        }

        public String getGasPrice() {
            return gasPrice;
        }

        public void setGasPrice(String gasPrice) {
            this.gasPrice = gasPrice;
        }

        public String getInputData() {
            return inputData;
        }

        public void setInputData(String inputData) {
            this.inputData = inputData;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public String getTxFee() {
            return txFee;
        }

        public void setTxFee(String txFee) {
            this.txFee = txFee;
        }

        public static class DataBean {
            /**
             * v : 27
             * r : bf2ac04c25fbf955ce796be3e079043cb3fb576a57027a17b1ca11281d60751a
             * s : 728ea26b372e95bc32e937a00ebd8b2fa22e909989866aaf396722a0a4ef4c9a
             */

            private int v;
            private String r;
            private String s;

            public int getV() {
                return v;
            }

            public void setV(int v) {
                this.v = v;
            }

            public String getR() {
                return r;
            }

            public void setR(String r) {
                this.r = r;
            }

            public String getS() {
                return s;
            }

            public void setS(String s) {
                this.s = s;
            }
        }
    }
}
