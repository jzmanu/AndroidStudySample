package manu.com.bind;

import android.app.Activity;

/**
 * @Desc:
 * @Author: Administrator
 * @Date: 2020/5/26 17:47.
 */
public class BindKnife {
    public static void bind(Activity activity) {
        // 获取activity的全限定类名
        String name = activity.getClass().getName();

        try {
            // 反射创建并注入
            Class<?> clazz = Class.forName(name + "_ViewBind");
            clazz.getConstructor(activity.getClass()).newInstance(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
