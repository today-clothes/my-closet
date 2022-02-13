import {
  SectionContainer,
  SectionInnerContainer,
} from '@components/Container/Container.styled';
import React from 'react';
import img from '@images/home/section1-1.png';
import styled from 'styled-components';
import { RoundButtonStyles as RoundButton } from '@components/Button/RoundButton.styles';

const Section = styled.section`
  padding: 120px 40px;
  display: flex;
  flex-direction: column;
  text-align: center;
  h2 {
    font-family: 'Song Myung', serif;
    font-size: 2rem;
    margin-bottom: 25px;
  }
  h3 {
    font-family: 'Nanum Gothic', sans-serif;
    line-height: 25px;
  }
  img {
    width: 50%;
    margin: 50px 0;
  }
`;

const HomeSection4 = () => {
  return (
    <SectionContainer backgroundColor="#D7D2D9">
      <SectionInnerContainer>
        <Section>
          <h2>멋진 친구들을 만날 수 있는 공간</h2>
          <h3>
            어떻게 입을지 잘 모르겠다면 추천과 검색을 이용해보세요
            <br />
            당신이 좋아하는 스타일대로 입은 멋진 친구들을 만날 수 있습니다.
          </h3>
          <div>
            <img src={img} alt="section4 image" />
          </div>
          <div>
            <RoundButton backgroundColor="#693686" size="large">
              Android용 다운로드
            </RoundButton>
          </div>
        </Section>
      </SectionInnerContainer>
    </SectionContainer>
  );
};

export default HomeSection4;
