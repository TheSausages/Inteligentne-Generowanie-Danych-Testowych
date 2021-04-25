package InsertCreation;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.*;
import java.lang.reflect.Parameter;
import java.nio.file.Files;
import java.nio.file.Path;

class TemporaryFileParameterResolver implements ParameterResolver {
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.PARAMETER})
    @interface Temporary {
        boolean deleteOnExit() default true;
    }
    @Override
    public boolean supportsParameter(
            ParameterContext parameterContext,
            ExtensionContext extensionContext)
            throws ParameterResolutionException {
        if (parameterContext.isAnnotated(Temporary.class)) {
            Parameter parameter = parameterContext.getParameter();
            Class<?> parameterType = parameter.getType();
            return File.class == parameterType
                    || Path.class == parameterType;
        }
        return false;
    }
    @Override
    public Object resolveParameter(
            ParameterContext parameterContext,
            ExtensionContext extensionContext)
            throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        Temporary temporary =
                parameter.getAnnotation(Temporary.class);
        Class<?> parameterType = parameter.getType();
        if (File.class == parameterType) {
            File file;
            try {
                file = File.createTempFile("tmp", null);
            } catch (final IOException ioe) {
                throw new ParameterResolutionException(
                        "failed to create a temporary file", ioe);
            }
            if (temporary.deleteOnExit()) {
                file.deleteOnExit();
            }
            return file;
        }
        if (Path.class == parameterType) {
            Path path;
            try {
                path = Files.createTempFile(null, null);
            } catch (final IOException ioe) {
                throw new ParameterResolutionException(
                        "failed to create a temporary file", ioe);
            }
            if (temporary.deleteOnExit()) {
                Runtime.getRuntime().addShutdownHook(
                        new Thread(() -> {
                            try {
                                Files.deleteIfExists(path);
                            } catch (final IOException ioe) {
                                throw new RuntimeException(ioe);
                            }
                        }));
            }
            return path;
        }
        throw new ParameterResolutionException(
                "failed to resolve parameter for " + parameterContext);
    }
}