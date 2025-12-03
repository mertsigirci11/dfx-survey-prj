package com.teamdefinex.dfxsurvey.application;

import com.teamdefinex.dfxsurvey.dto.result.Result;

import java.util.Dictionary;

public interface NotificationService {
    Result<Void> send(String title, String to, String template, Dictionary<String, String> parameters);
}
