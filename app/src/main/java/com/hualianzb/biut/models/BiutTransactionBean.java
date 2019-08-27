package com.hualianzb.biut.models;

import java.io.Serializable;
import java.util.List;

/**
 * Date:2018/11/28
 * auther:wangtianyun
 * describe:
 */
public class BiutTransactionBean implements Serializable {
    /**
     * jsonrpc : 2.0
     * id : 1
     * result : {"status":"1","message":"OK","resultInChain":[],"resultInPool":[{"TxHash":"7a69cf568a0f9731411d5ffe923cd789f689140b5199545e568e43b81fc62e0b","TxReceiptStatus":"pending","Version":"","TimeStamp":1557569102514,"TxFrom":"659c0673eaf00189f3dc6adfa96935c2eccb0933","TxTo":"69e061e5ebc42b23051ebe6fa9cc0bdf3010caae","Value":"10","GasLimit":"0","GasUsedByTxn":"0","GasPrice":"0","TxFee":"0.02","Nonce":"0","InputData":"","Signature":{"r":"2a6e2a0bd21137fda9c29fefbe918b951d4f29db4b77e431228c1134d7797788","s":"4296cb2fb80fe0ba9dcd4aab81d6d66cc0a02af22cac1eb841ab55abf9cb2ee3","v":27},"TxHeight":""}]}
     */

    private String jsonrpc;
    private int id;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * status : 1
         * message : OK
         * resultInChain : []
         * resultInPool : [{"TxHash":"7a69cf568a0f9731411d5ffe923cd789f689140b5199545e568e43b81fc62e0b","TxReceiptStatus":"pending","Version":"","TimeStamp":1557569102514,"TxFrom":"659c0673eaf00189f3dc6adfa96935c2eccb0933","TxTo":"69e061e5ebc42b23051ebe6fa9cc0bdf3010caae","Value":"10","GasLimit":"0","GasUsedByTxn":"0","GasPrice":"0","TxFee":"0.02","Nonce":"0","InputData":"","Signature":{"r":"2a6e2a0bd21137fda9c29fefbe918b951d4f29db4b77e431228c1134d7797788","s":"4296cb2fb80fe0ba9dcd4aab81d6d66cc0a02af22cac1eb841ab55abf9cb2ee3","v":27},"TxHeight":""}]
         */

        private String status;
        private String message;
        private List<FatherBean> resultInChain;
        private List<FatherBean> resultInPool;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<FatherBean> getResultInChain() {
            return resultInChain;
        }

        public void setResultInChain(List<FatherBean> resultInChain) {
            this.resultInChain = resultInChain;
        }

        public List<FatherBean> getResultInPool() {
            return resultInPool;
        }

        public void setResultInPool(List<FatherBean> resultInPool) {
            this.resultInPool = resultInPool;
        }

        public static class FatherBean implements Serializable {
            /**
             * TxHash : 7a69cf568a0f9731411d5ffe923cd789f689140b5199545e568e43b81fc62e0b
             * TxReceiptStatus : pending
             * Version :
             * TimeStamp : 1557569102514
             * TxFrom : 659c0673eaf00189f3dc6adfa96935c2eccb0933
             * TxTo : 69e061e5ebc42b23051ebe6fa9cc0bdf3010caae
             * Value : 10
             * GasLimit : 0
             * GasUsedByTxn : 0
             * GasPrice : 0
             * TxFee : 0.02
             * Nonce : 0
             * InputData :
             * Signature : {"r":"2a6e2a0bd21137fda9c29fefbe918b951d4f29db4b77e431228c1134d7797788","s":"4296cb2fb80fe0ba9dcd4aab81d6d66cc0a02af22cac1eb841ab55abf9cb2ee3","v":27}
             * TxHeight :
             */

            private String TxHash;
            private String TxReceiptStatus;
            private String Version;
            private long TimeStamp;
            private String TxFrom;
            private String TxTo;
            private String Value;
            private String GasLimit;
            private String GasUsedByTxn;
            private String GasPrice;
            private String TxFee;
            private String Nonce;
            private String InputData;
            private SignatureBean Signature;
            private String BlockHash;
            private String TxHeight;
            private int BlockNumber;

            public String getBlockHash() {
                return BlockHash;
            }

            public void setBlockHash(String blockHash) {
                BlockHash = blockHash;
            }

            public String getTxHash() {
                return TxHash;
            }

            public void setTxHash(String TxHash) {
                this.TxHash = TxHash;
            }

            public String getTxReceiptStatus() {
                return TxReceiptStatus;
            }

            public void setTxReceiptStatus(String TxReceiptStatus) {
                this.TxReceiptStatus = TxReceiptStatus;
            }

            public String getVersion() {
                return Version;
            }

            public void setVersion(String Version) {
                this.Version = Version;
            }

            public long getTimeStamp() {
                return TimeStamp;
            }

            public void setTimeStamp(long TimeStamp) {
                this.TimeStamp = TimeStamp;
            }

            public String getTxFrom() {
                return TxFrom;
            }

            public void setTxFrom(String TxFrom) {
                this.TxFrom = TxFrom;
            }

            public String getTxTo() {
                return TxTo;
            }

            public void setTxTo(String TxTo) {
                this.TxTo = TxTo;
            }

            public String getValue() {
                return Value;
            }

            public void setValue(String Value) {
                this.Value = Value;
            }

            public String getGasLimit() {
                return GasLimit;
            }

            public void setGasLimit(String GasLimit) {
                this.GasLimit = GasLimit;
            }

            public String getGasUsedByTxn() {
                return GasUsedByTxn;
            }

            public void setGasUsedByTxn(String GasUsedByTxn) {
                this.GasUsedByTxn = GasUsedByTxn;
            }

            public String getGasPrice() {
                return GasPrice;
            }

            public void setGasPrice(String GasPrice) {
                this.GasPrice = GasPrice;
            }

            public String getTxFee() {
                return TxFee;
            }

            public void setTxFee(String TxFee) {
                this.TxFee = TxFee;
            }

            public String getNonce() {
                return Nonce;
            }

            public void setNonce(String Nonce) {
                this.Nonce = Nonce;
            }

            public String getInputData() {
                return InputData;
            }

            public void setInputData(String InputData) {
                this.InputData = InputData;
            }

            public SignatureBean getSignature() {
                return Signature;
            }

            public void setSignature(SignatureBean Signature) {
                this.Signature = Signature;
            }

            public String getTxHeight() {
                return TxHeight;
            }

            public void setTxHeight(String TxHeight) {
                this.TxHeight = TxHeight;
            }

            public static class SignatureBean {
                /**
                 * r : 2a6e2a0bd21137fda9c29fefbe918b951d4f29db4b77e431228c1134d7797788
                 * s : 4296cb2fb80fe0ba9dcd4aab81d6d66cc0a02af22cac1eb841ab55abf9cb2ee3
                 * v : 27
                 */

                private String r;
                private String s;
                private int v;

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

                public int getV() {
                    return v;
                }

                public void setV(int v) {
                    this.v = v;
                }
            }

            public int getBlockNumber() {
                return BlockNumber;
            }

            public void setBlockNumber(int blockNumber) {
                BlockNumber = blockNumber;
            }
        }
    }
    /**
     * jsonrpc : 2.0
     * id : 1
     * result : {"status":"1","message":"OK","resultInChain":[{"TxHash":"0e382879dcca21c9eb31608beaa8659a01b14212febb2f773c52bbd846beed51","TxReceiptStatus":"success","Version":"0.1","TimeStamp":1557150994370,"TxFrom":"d39b0f5a65c19ba5282606e69e22aff3dbf403ea","TxTo":"0338ae6ee49463c76b4eb31d5714705d01ba26eb","Value":"2","GasLimit":"0","GasUsedByTxn":"0","GasPrice":"0","TxFee":"0.02","Nonce":"0","InputData":"","Signature":{"r":"2e4568fe52a9546680a946092dc02d2e16c7b486352d41d360adf567f7bead7a","s":"5bec2480bb5bc48730f9550b7d464fcb7b14d93278b4cf62732413dbda965483","v":27},"TxHeight":0,"BlockNumber":289,"BlockHash":"fa5b372e55988163a7d90285236d2474e3a141a6087e207da89e85753df3c08e","CumulativeGasUsed":"0","TransactionIndex":0,"ContractAddress":"","Confirmations":""}],"resultInPool":[]}
     */

}
