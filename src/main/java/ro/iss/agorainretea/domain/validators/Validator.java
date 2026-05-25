package ro.iss.agorainretea.domain.validators;

public interface Validator<T> {
    public void validate(T entity);
}
