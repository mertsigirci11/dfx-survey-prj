package com.teamdefinex.dfxsurvey.application;

import com.teamdefinex.dfxsurvey.dto.LoginResponseDTO;
import com.teamdefinex.dfxsurvey.dto.result.Result;

public interface AuthService {
    Result<LoginResponseDTO> login(String username, String password);
}
