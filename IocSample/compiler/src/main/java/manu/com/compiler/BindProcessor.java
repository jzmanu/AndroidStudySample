package manu.com.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import manu.com.api.Bind;

/**
 * BindProcessor
 */
@AutoService(Processor.class)
public class BindProcessor extends AbstractProcessor {
    private Elements mElements;
    private Filer mFiler;
    private Messager mMessager;

    // 存储某个类下面对应的BindModel
    private Map<TypeElement, List<BindModel>> mTypeElementMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnvironment.getMessager();
        print("init");
        // 初始化Processor

        mElements = processingEnvironment.getElementUtils();
        mFiler = processingEnvironment.getFiler();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        print("getSupportedSourceVersion");
        // 返回使用的Java版本
        return SourceVersion.RELEASE_8;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        print("getSupportedAnnotationTypes");
        // 返回要处理的的所有的注解名称
        Set<String> set = new HashSet<>();
        set.add(Bind.class.getCanonicalName());
        return set;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        print("process");
        mTypeElementMap.clear();
        // 获取指定Class类型的Element
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Bind.class);
        // 遍历将符合条件的Element存储起来
        for (Element element : elements) {
            // 获取Element对应类的全限定类名
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
            print("process typeElement name:"+typeElement.getSimpleName());
            List<BindModel> modelList = mTypeElementMap.get(typeElement);
            if (modelList == null) {
                modelList = new ArrayList<>();
            }
            modelList.add(new BindModel(element));
            mTypeElementMap.put(typeElement, modelList);
        }

        print("process mTypeElementMap size:" + mTypeElementMap.size());

        // Java文件生成
        mTypeElementMap.forEach((typeElement, bindModels) -> {
            print("process bindModels size:" + bindModels.size());
            // 获取包名
            String packageName = mElements.getPackageOf(typeElement).getQualifiedName().toString();
            // 生成Java文件的文件名
            String className = typeElement.getSimpleName().toString();
            String newClassName = className + "_ViewBind";

            // MethodSpec
            MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.bestGuess(className), "target");
            bindModels.forEach(model -> constructorBuilder.addStatement("target.$L=($L)target.findViewById($L)",
                    model.getViewFieldName(), model.getViewFieldType(), model.getResId()));
            // typeSpec
            TypeSpec typeSpec = TypeSpec.classBuilder(newClassName)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(constructorBuilder.build())
                    .build();
            // JavaFile
            JavaFile javaFile = JavaFile.builder(packageName, typeSpec)
                    .addFileComment("AUTO Create")
                    .build();

            try {
                javaFile.writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return true;
    }

    private void print(String message) {
        if (mMessager == null) return;
        mMessager.printMessage(Diagnostic.Kind.NOTE, message);
    }
}

