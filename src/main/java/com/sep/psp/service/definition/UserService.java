package com.sep.psp.service.definition;

import com.sep.psp.model.User;

public interface UserService {
    User getUserById(Integer id);
    User getCurrentUser();
}
