package manu.com.compiler;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import manu.com.api.Bind;

/**
 * @Desc: BindModel
 * @Author: Administrator
 * @Date: 2020/5/26 15:28.
 */
public class BindModel {
    // 成员变量Element
    private VariableElement mViewFieldElement;
    // 成员变量类型
    private TypeMirror mViewFieldType;
    // View的资源Id
    private int mResId;

    public BindModel(Element element) {
        // 校验Element是否是成员变量
        if (element.getKind() != ElementKind.FIELD) {
            throw new IllegalArgumentException("element is not FIELD");
        }
        // 成员变量Element
        mViewFieldElement = (VariableElement) element;
        // 成员变量类型
        mViewFieldType = element.asType();
        // 获取注解的值
        Bind bind = mViewFieldElement.getAnnotation(Bind.class);
        mResId = bind.value();
    }

    public int getResId(){
        return mResId;
    }

    public String getViewFieldName(){
        return mViewFieldElement.getSimpleName().toString();
    }

    public TypeMirror getViewFieldType(){
        return mViewFieldType;
    }
}
