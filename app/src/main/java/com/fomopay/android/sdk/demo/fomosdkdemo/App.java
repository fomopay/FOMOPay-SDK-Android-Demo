/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2018 FOMO Pay Pte. Ltd.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fomopay.android.sdk.demo.fomosdkdemo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.fomopay.android.sdk.FOMOPay;

/**
 * Created by shan.he on 20/11/17.
 */

public class App extends Application {

    static public final String API_USERNAME = "";
    static public final String API_SECRET = "";


    @Override
    public void onCreate() {
        super.onCreate();
        FOMOPay.SHOW_LOGS = true;
        FOMOPay.init(this, API_USERNAME);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
