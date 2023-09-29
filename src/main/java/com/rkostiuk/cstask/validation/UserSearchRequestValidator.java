package com.rkostiuk.cstask.validation;

import com.rkostiuk.cstask.dto.request.UserSearchRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UserSearchRequestValidator extends CustomValidator<UserSearchRequest> {
    @Override
    public boolean supports(Class<?> type) {
        return type.equals(UserSearchRequestValidator.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var request = (UserSearchRequest) target;
        if (!request.fromIncluding().isBefore(request.toExcluding())) {
            errors.rejectValue("toExcluding", "FromToPeriod.toExcluding",
                    "'toExcluding' has to be after 'fromIncluding'");
        }
    }
}
