package org.iiai.ne.service;

import org.iiai.ne.model.LogInData;

public interface SessionService {
    String createSession(LogInData logInData);

    String refreshSession(int userId);
}
