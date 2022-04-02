import com.google.auto.service.AutoService;
import com.google.protobuf.GeneratedMessageLite;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.swing.*;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes({/*"AutoProvideProto", "AutoProvideProto2", "AutoProvideProto3",*/ "AutoProvideProtoEnum"})
@SupportedSourceVersion(SourceVersion.RELEASE_16)
@AutoService(Processor.class)
public class AnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {

        System.out.println("root elements: " + roundEnv.getRootElements());
        for (Element e : roundEnv.getRootElements()) {
            TypeElement te = findEnclosingTypeElement(e);
            System.out.printf("\nScanning Type %s\n\n",
                    te.getQualifiedName());
            AutoProvideProtoEnum annotation1 = te.getAnnotation(AutoProvideProtoEnum.class);
            System.out.println("Annotation on that TypeElement: " + annotation1);
//            if (annotation1 != null) {
//                try {
//                    Class<? extends GeneratedMessageLite> aClass = annotation1.value();
//                    System.out.println("got class" + aClass);
//                } catch (MirroredTypeException mte) {
//                    TypeMirror typeMirror = mte.getTypeMirror();
//                    System.out.println("got type mirror " + typeMirror);
//                }
//            }
            System.out.println("enclosed elements: " + te.getEnclosedElements());
            ElementFilter.typesIn(te.getEnclosedElements()).forEach(enclosedTypeElement -> {
                System.out.println("enclosed TypeElement: " + enclosedTypeElement);
                List<? extends Element> enclosedElements = enclosedTypeElement.getEnclosedElements();
                System.out.println("inner enclosed element (in enclosed typeElement) " + enclosedElements);
                enclosedElements.stream().filter(ee -> ee.getSimpleName().toString().startsWith("get")).forEach(System.out::println);
            });
//            te.getEnclosedElements().forEach(enclosedElement -> {
//                System.out.println("enclosedElement " + enclosedElement);
//                AutoProvideProtoEnum annotation = enclosedElement.getAnnotation(AutoProvideProtoEnum.class);
//                System.out.println("annotation on that element: " + annotation);
//                if (annotation!= null) {
//                    System.out.println("element kind: " + enclosedElement.getKind());
//                }
//            });
//            for (ExecutableElement ee : ElementFilter.methodsIn(
//                    te.getEnclosedElements())) {
//                AutoProvideProtoEnum action = ee.getAnnotation(AutoProvideProto.class);
//
//                System.out.printf(
//                        "%s Action value = %s\n",
//                        ee.getSimpleName(),
//                        action == null ? null : action.value());
//            }
        }
//        System.out.println("annotations " + annotations);
//        annotations.forEach(typeElement -> System.out.println("qualified name " + typeElement.getQualifiedName()));
//        annotations.forEach(typeElement -> {
//            List<? extends Element> enclosedElements = typeElement.getEnclosedElements();
//            System.out.println("enclosed elements " + enclosedElements);
//            ElementFilter.methodsIn(enclosedElements).forEach(ee -> {
//                System.out.println("executable element: " + ee);
//                AutoProvideProto action = ee.getAnnotation(AutoProvideProto.class);
//                System.out.println("executable element annotation of autoProvideProto: " + action);
//                System.out.println("ee simple name " + ee.getSimpleName());
//                typeElement.
//                if (action == null) {
//                    // Look for the overridden method
//                    ExecutableElement oe = getExecutableElement(typeElement, ee.getSimpleName());
//                    if (oe != null) {
//                        action = oe.getAnnotation(AutoProvideProto.class);
//                    }
//                }
//
//                TypeMirror value = null;
//                if (action != null) {
//                    try {
//                        System.out.println("got action value! " + action.value());
//                    } catch (MirroredTypeException mte) {
//                        value = mte.getTypeMirror();
//                    }
//                }
//
//                System.out.printf("%s Action value = %s\n", ee.getSimpleName(), value);
//            });
//            System.out.println(enclosedElements);
//
//            AutoProvideProto annotation = typeElement.getAnnotation(AutoProvideProto.class);
//            System.out.println(annotation);
//            if (annotation != null) {
//                System.out.println(typeElement.getAnnotation(AutoProvideProto.class).value());
//            }
//            Set<? extends Element> annotatedElements
//                    = roundEnv.getElementsAnnotatedWith(typeElement);
//            System.out.println(annotatedElements);
//        });

        return false;
    }

    public ExecutableElement getExecutableElement(final TypeElement typeElement,
                                                  final Name name) {
        TypeElement te = typeElement;
        do {
            te = (TypeElement) processingEnv.getTypeUtils().asElement(
                    te.getSuperclass());
            if (te != null) {
                for (ExecutableElement ee : ElementFilter.methodsIn(
                        te.getEnclosedElements())) {
                    if (name.equals(ee.getSimpleName()) && ee.getParameters().isEmpty()) {
                        return ee;
                    }
                }
            }
        } while (te != null);
        return null;
    }

    public static TypeElement findEnclosingTypeElement(Element e) {
        while (e != null && !(e instanceof TypeElement)) {
            e = e.getEnclosingElement();
        }
        return (TypeElement) e;
    }
}