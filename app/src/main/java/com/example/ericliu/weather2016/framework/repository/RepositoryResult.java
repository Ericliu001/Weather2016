package com.example.ericliu.weather2016.framework.repository;

/**
 * Created by ericliu on 12/04/2016.
 */
public class RepositoryResult<T> {
    private  Specification specification;
    private T data;
    private Object error;

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }


}
