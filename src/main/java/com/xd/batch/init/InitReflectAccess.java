package com.xd.batch.init;

import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import com.xd.batch.bean.AccessWrapper;
import com.xd.batch.bean.DataProperties;
import com.xd.batch.cache.AbstractCacheDefinition;
import com.xd.batch.util.ClazzLoaderUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitReflectAccess implements InitializingBean{

    @Autowired
    private DataProperties dataProperties;

    @Autowired
    private AbstractCacheDefinition abstractCacheDefinition;


    @Override
    public void afterPropertiesSet() throws Exception {
        initReflectClass();
    }

    public void initReflectClass() {
        String packageNames = dataProperties.getPackageName();
        String[] packageNameArr = packageNames.split(";");
        List<Class> allClassByPackageName = ClazzLoaderUtil.getAllClassByPackageName(packageNameArr);

        allClassByPackageName.forEach(clazz->{
            cacheMethod(clazz);
            cacheField(clazz);
        });

    }

    private void cacheMethod(Class<?> aClass){
        MethodAccess methodAccess = MethodAccess.get(aClass);
        String[] methodNames = methodAccess.getMethodNames();
        for (int i = 0; i < methodNames.length; i++) {
            AccessWrapper<MethodAccess> accessWrapper = new AccessWrapper(methodAccess, methodNames[i], i);
            abstractCacheDefinition.getLocalCache().putAccess(aClass,accessWrapper);
        }
    }

    private void cacheField(Class<?> aClass){
        FieldAccess fieldAccess = FieldAccess.get(aClass);
        String[] fieldNames = fieldAccess.getFieldNames();
        for (int i = 0; i < fieldNames.length; i++) {
            AccessWrapper<FieldAccess> accessWrapper = new AccessWrapper(fieldAccess, fieldNames[i], i);
            abstractCacheDefinition.getLocalCache().putAccess(aClass,accessWrapper);
        }
    }


}
