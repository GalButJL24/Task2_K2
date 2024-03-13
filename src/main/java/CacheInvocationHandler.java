import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

public class CacheInvocationHandler implements InvocationHandler {
    private   Boolean changed = true;
    // Имплементация интерфейса
    private final Object  targetObject;

    // Перечень методов с аннотацией  Mutator
    private final List<Method> methodsMutatorList = new ArrayList<>();
    // Перечень методов с аннотацией  Cache
    private final List<Method> methodsCacheList = new ArrayList<>();
    //Перечень методов с аннотацией  Cache( Map на случай, если в классе два метода  Cache)
    private final Map<Method, Object> methodCaches = new HashMap<>();
    // Сравнить на  идентичность методы (имя + пар-ры) (сравнение ссылок  не работает)
    private boolean methodCmp(Method method1, Method method2) {
        return method1.getName().equals(method2.getName()) &&
                Arrays.toString(method1.getParameterTypes()).equals(Arrays.toString(method2.getParameterTypes()));
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object  resultOfInvocation;                               // результат выполнения

        Method curMethod = targetObject.getClass().getMethod(method.getName(),method.getParameterTypes());
        // Если вызван метод  с аннот. CACHE
        if (curMethod.isAnnotationPresent(Cache.class)) {
            if (!methodCaches.containsKey(method)  || changed) {
                // Если метод  с аннот. CACHE  был вызван первый раз или значение было изменено методом с аннотацией Mutator
                resultOfInvocation = method.invoke(targetObject, args);
                // Фиксируем инфо о вычисленном значении
                methodCaches.put(method, resultOfInvocation);
                System.out.println("Значение вычислено = " +  (double)resultOfInvocation);
            }
            else
            {
                // Получаем сохраненное значение
                resultOfInvocation =  methodCaches.get(method);
                System.out.println("Вызов из кэша = " + (double)resultOfInvocation);
            }
            changed = false;
        } else {
            // Во всех остальных случаях не вмешиваемся в работу
            if (curMethod.isAnnotationPresent(Mutator.class)) {
                changed = true;
                System.out.println("Вызов метода , помеченного аннотацией Mutator ");
            }
            // Вычисление значения
            resultOfInvocation = method.invoke(targetObject, args);
        }
        return resultOfInvocation;
    }

    public CacheInvocationHandler(Object targetObject) {
        this.targetObject = targetObject;


    }



}
