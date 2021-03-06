package org.javers.core.metamodel.scanner;

import org.javers.common.reflection.JaversGetter;
import org.javers.common.reflection.ReflectionUtil;
import org.javers.core.metamodel.property.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author pawel szymczyk
 */
class BeanBasedPropertyScanner extends PropertyScanner {

    private final AnnotationNamesProvider annotationNamesProvider;

    BeanBasedPropertyScanner(AnnotationNamesProvider annotationNamesProvider) {
        this.annotationNamesProvider = annotationNamesProvider;
    }

    @Override
    public PropertyScan scan(Class<?> managedClass, boolean ignoreDeclaredProperties) {
        List<JaversGetter> getters = ReflectionUtil.getAllGetters(managedClass);
        List<Property> beanProperties = new ArrayList<>();

        for (JaversGetter getter : getters) {
            boolean isIgnoredInType = ignoreDeclaredProperties && getter.getDeclaringClass().equals(managedClass);
            boolean hasTransientAnn = annotationNamesProvider.hasTransientPropertyAnn(getter.getAnnotationTypes());
            boolean hasShallowReferenceAnn = annotationNamesProvider.hasShallowReferenceAnn(getter.getAnnotationTypes());

            Optional<String> customPropertyName = annotationNamesProvider.findPropertyNameAnnValue(getter.getAnnotations());
            beanProperties.add(new Property(getter, hasTransientAnn || isIgnoredInType, hasShallowReferenceAnn, customPropertyName));

        }
        return new PropertyScan(beanProperties);
    }
}
