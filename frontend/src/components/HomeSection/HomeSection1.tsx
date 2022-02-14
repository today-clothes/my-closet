import React from 'react';
import Header from '@components/Header';
import {
  SectionContainer,
  SectionInnerContainer,
} from '@components/Container/Container.styled';
import { RoundButtonStyles as RoundButton } from '@components/Button/RoundButton.styles';
import styled from 'styled-components';
import leftImg from '@images/home/section1-1.png';
import rightImg from '@images/home/section1-2.png';

const TextSection = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 100px 0 40px 0;
  h1 {
    font-family: 'Song Myung', serif;
    font-size: 3rem;
    color: white;
    margin-bottom: 20px;
  }
  h2 {
    color: white;
    font-family: 'Nanum Gothic', sans-serif;
    text-align: center;
    line-height: 25px;
  }
`;

const ButtonSection = styled.section`
  display: flex;
  justify-content: center;
  padding-bottom: 100px;
  & > button {
    margin: 5px;
  }
`;

const BackgroundImageContainer = styled.div`
  position: absolute;
  width: 100%;
  max-width: 1600px;
  bottom: -20px;
  display: flex;
  justify-content: space-between;
  align-items: end;
  z-index: 1;
  div {
    width: 40%;
  }
  img {
    width: 100%;
  }
`;

const HomeSection1 = () => {
  return (
    <>
      <SectionContainer backgroundColor="#8E5F9F">
        <SectionInnerContainer>
          <Header />
          <TextSection>
            <h1>오늘 뭐 입지?</h1>
            <h2>
              오늘도 옷장 앞에 선 그대.
              <br />
              어질러진 방을 보니 고민에 빠지셨군요!
              <br />
              만약, 쉽고 빠르게 스타일을 고를 수만 있다면 얼마나 좋을까요?
              <br />내 손 안에 들어온 옷장, 오늘의 옷을 소개합니다.
            </h2>
          </TextSection>
          <ButtonSection>
            <RoundButton
              backgroundColor="#CCB0D5"
              color="#23272a"
              hoverColor="#693686"
              size="large"
              fontWeight={600}
            >
              Android용 다운로드
            </RoundButton>
            <RoundButton
              backgroundColor="#693686"
              size="large"
              fontWeight={600}
            >
              웹브라우저에서 시작하기
            </RoundButton>
          </ButtonSection>
        </SectionInnerContainer>
        <BackgroundImageContainer>
          <div>
            <img src={leftImg} alt="" />
          </div>
          <div>
            <img src={rightImg} alt="" />
          </div>
        </BackgroundImageContainer>
      </SectionContainer>
    </>
  );
};

export default HomeSection1;
