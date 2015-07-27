package org.exoplatform.bch.jpadynamicentitiesregistration;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 * exo@exoplatform.com
 * 7/22/15
 */
@SupportedAnnotationTypes("org.exoplatform.bch.jpadynamicentitiesregistration.ExoEntity")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ExoEntityProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Writer writer = null;
        if (!roundEnv.processingOver()) {
            try {
                FileObject file = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", "exo-jpa-entities/entities.idx");
                writer = file.openWriter();
                for (Element element : roundEnv.getElementsAnnotatedWith(ExoEntity.class)) {
                    writer.append(element.asType().toString()+"\n");
                    System.out.println(element.asType());
                }
            } catch (FilerException e) {
                e.printStackTrace();
                //TODO Second round: the file as already been created
            } catch (IOException e) {
                e.printStackTrace(); //TODO
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace(); //TODO
                    }
                }
            }
        }
        return true; //No need to process these annotations again
    }
}
