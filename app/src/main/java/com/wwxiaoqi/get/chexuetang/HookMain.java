package com.wwxiaoqi.get.chexuetang;

import android.content.Context;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class HookMain implements IXposedHookLoadPackage {

    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {

        if (lpparam.packageName.equals("cn.com.drivedu.chexuetang")) {
            XposedBridge.log("Hook cn.com.drivedu.chexuetang...");


            Class<?> clazz = Class.forName("com.lcwh.questionbank.db.SharedPreferencesDB", false, lpparam.classLoader);
            XposedHelpers.findAndHookMethod(clazz, "setisOpenVip", "boolean", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    param.args[0] = true;
                }
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }
            });

            XposedHelpers.findAndHookMethod(clazz, "isOpenVip", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) { }
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.setResult(true);
                }
            });

            Class<?> clazz2 = Class.forName("cn.com.drivedu.chexuetang.main.bean.RuleModel", false, lpparam.classLoader);
            XposedHelpers.findAndHookMethod(clazz2, "getIsLoadAd", Context.class, int.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) { }
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.setResult(false);
                }
            });
            XposedBridge.log("Hook done.");
        }
    }
}