package com.oclothes.domain.user.service;

import com.oclothes.domain.user.dao.EmailAuthenticationCodeRepository;
import com.oclothes.domain.user.dao.UserRepository;
import com.oclothes.domain.user.domain.EmailAuthenticationCode;
import com.oclothes.domain.user.dto.UserMapper;
import com.oclothes.domain.user.exception.EmailAuthenticationCodeNotFoundException;
import com.oclothes.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.oclothes.domain.user.dto.UserDto.SignUpResponse;

@RequiredArgsConstructor
@Transactional
@Service
public class EmailAuthenticationCodeServiceImpl implements EmailAuthenticationCodeService {
    private final EmailAuthenticationCodeRepository repository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public EmailAuthenticationCode save(EmailAuthenticationCode emailAuthenticationCode) {
        return this.repository.save(emailAuthenticationCode);
    }

    @Override
    public SignUpResponse emailAuthentication(String email, String code) {
        return this.userMapper.toSignUpResponse(this.repository
                .findByUser(this.userRepository.findByEmail_Value(email).orElseThrow(UserNotFoundException::new))
                .orElseThrow(EmailAuthenticationCodeNotFoundException::new)
                .authentication(code));
    }
}
