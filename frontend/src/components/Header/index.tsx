import React from 'react';
import { HeaderContainer } from './Header.styles';
import { RoundButtonStyles as RoundButton } from '@components/Button/RoundButton.styles';
import logo from '@images/logo-white.png';
import styled from 'styled-components';

const LogoContainer = styled.div`
  display: flex;
  align-items: center;
  width: 100px;
  height: 100%;
  img {
    width: 50%;
  }
`;

const Header = () => {
  return (
    <HeaderContainer>
      <LogoContainer>
        <img src={logo} alt="logo" />
      </LogoContainer>
      <div>
        <RoundButton color="#8E5F9F">로그인</RoundButton>
      </div>
    </HeaderContainer>
  );
};

export default Header;
