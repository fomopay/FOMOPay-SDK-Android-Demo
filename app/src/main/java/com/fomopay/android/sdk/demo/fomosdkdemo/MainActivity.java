/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2017 FOMO Pay Pte. Ltd.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fomopay.android.sdk.demo.fomosdkdemo;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fomopay.android.sdk.FOMOConsumer;
import com.fomopay.android.sdk.FOMOPay;
import com.fomopay.android.sdk.FOMOPayException;
import com.fomopay.android.sdk.FOMOPayOrder;
import com.fomopay.android.sdk.FOMOPayRequest;
import com.fomopay.android.sdk.FOMOPayResponse;
import com.fomopay.android.sdk.RandomString;
import com.fomopay.android.sdk.SignUtils;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference);

        Button checkout = (Button) findViewById(R.id.cartCheckoutBtn);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setMessage("Payment successful.")
                        .setTitle("Congratulations");

                AlertDialog dialog = builder.create();

                final FOMOPayOrder order = new FOMOPayOrder();
                order.merchant = App.API_USERNAME;
                order.price = "1.00";
                order.description = "description";
                order.transaction = "whatever" + UUID.randomUUID().toString();

                order.callback_url = "https://callback.url";
                order.currency_code = "sgd";

                order.type = "sale";

                order.nonce = new RandomString().nextString();

                order.tag = "tag";
                order.upstreams = "netspay";

                order.signature = SignUtils.sign(order, App.API_SECRET);

                // Demo code for creating new order. The algorithm should be implemented in your server.
                // DO NOT USE in production for security.

                FOMOPay.createOrder(MainActivity.this, order,
                        new FOMOConsumer<String>() {
                            @Override
                            public void accept(String s) {
                                pay(s);
                            }
                        },
                        new FOMOConsumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                Log.e("---", "", throwable);

                                StringBuilder sb = new StringBuilder();
                                if (throwable instanceof FOMOPayException) {
                                    sb.append("FOMOPay ErrorCode:")
                                            .append(((FOMOPayException) throwable).getErrorCode())
                                            .append(" ");
                                }
                                sb.append(throwable.getMessage());
                                Toast.makeText(MainActivity.this, sb.toString(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

    }

    private void pay(String paymentId) {
        FOMOPayRequest payRequest = new FOMOPayRequest();
        payRequest.payment_id = paymentId;
        payRequest.merchant = App.API_USERNAME;
        payRequest.nonce = new RandomString().nextString();
        payRequest.timestamp = String.valueOf(System.currentTimeMillis());
        payRequest.signature = SignUtils.sign(payRequest, App.API_SECRET);
        FOMOPay.pay(this, payRequest,
                new FOMOConsumer<FOMOPayResponse>() {
                    @Override
                    public void accept(FOMOPayResponse payResponse) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                        builder.setMessage("Payment successful.")
                                .setTitle("Congratulations");

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                },
                new FOMOConsumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e("---", "", throwable);
                        StringBuilder sb = new StringBuilder();
                        if (throwable instanceof FOMOPayException) {
                            sb.append("FOMOPay ErrorCode:")
                                    .append(((FOMOPayException) throwable).getErrorCode())
                                    .append(" ");
                        }
                        sb.append(throwable.getMessage());
                        Toast.makeText(MainActivity.this, sb.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}
