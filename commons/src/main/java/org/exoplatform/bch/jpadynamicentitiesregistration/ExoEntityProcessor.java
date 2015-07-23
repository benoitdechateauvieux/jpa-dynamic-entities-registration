package org.exoplatform.bch.jpadynamicentitiesregistration;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.FilerException;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
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
@SupportedAnnotationTypes("org.exoplatform.bch.jpadynamicentitiesregistration.ExoJpaEntity")

public class ExoEntityProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Writer writer = null;
        try {
            FileObject file = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", "exo-jpa-entities/entities.idx");
            writer = file.openWriter();
            for (Element element : roundEnv.getElementsAnnotatedWith(ExoJpaEntity.class)) {
                writer.append(element.asType().toString());
                System.out.println(element.asType());
            }
        } catch (FilerException e) {
            //Second round: the file as already been created
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
        return true; //No need to process these annotations again
    }
}
