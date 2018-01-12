# PrettyOKHttp
真正的实现一句话OKHttp,Post,get,上传JSON,图片





    @Override
    public void JustPost() {

        new HttpUtils().DoPost(mapdata, myOKHttp, url, handler, HTTPSUCC, "");
    }

    @Override
    public void JustGet() {
        new HttpUtils().DoGet(myOKHttp, url, handler, HTTPSUCC);
    }

    @Override
    public void JustPostJson() {
        new HttpUtils().UpJsonString(myOKHttp, url, handler, HTTPSUCC, "你的Json数据", "");
    }
