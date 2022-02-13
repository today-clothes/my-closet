import React from 'react';
import styled from 'styled-components';
import {
  SectionContainer,
  SectionInnerContainer,
} from '@components/Container/Container.styled';
import logo from '@images/footer-logo.png';

const StyledBottom = styled.footer`
  padding: 80px 0 64px;
  display: flex;
  justify-content: space-between;
  color: #ffffff;
  font-family: 'Nanum Gothic', serif;
  ul {
    h5,
    li {
      margin: 10px 0 0;
    }
  }

  li {
    color: #d0d0d0;
  }

  img {
    width: 100px;
  }
`;

const Footer = () => {
  return (
    <SectionContainer backgroundColor="#693686">
      <SectionInnerContainer>
        <StyledBottom>
          <div>
            <img src={logo} alt="footer logo" />
          </div>
          <div className="spacer"></div>
          <ul>
            <h5>제품</h5>
            <li>다운로드</li>
            <li>상태</li>
          </ul>
          <ul>
            <h5>회사</h5>
            <li>소개</li>
            <li>제품</li>
            <li>상표</li>
            <li>뉴스</li>
          </ul>
          <ul>
            <h5>정책</h5>
            <li>이용약관</li>
            <li>개인 정보 보호 정책</li>
            <li>쿠키 설정</li>
            <li>지침</li>
            <li>라이센스</li>
          </ul>
        </StyledBottom>
      </SectionInnerContainer>
    </SectionContainer>
  );
};

export default Footer;
