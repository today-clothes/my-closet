import {
  SectionContainer,
  SectionInnerContainer,
} from '@components/Container/Container.styled';
import React from 'react';
import styled from 'styled-components';
import mockImg from '@images/home/section3.png';

const Section = styled.section`
  display: flex;
  justify-content: space-between;
  padding: 120px 40px;
`;

const TextSection = styled.article`
  display: flex;
  flex-direction: column;
  justify-content: center;
  & > h2 {
    font-family: 'Song Myung', serif;
    font-size: 2rem;
    margin-bottom: 25px;
  }

  & > h3 {
    font-family: 'Nanum Gothic', sans-serif;
    line-height: 25px;
  }
`;

const ImageSection = styled.article`
  width: 50%;
  img {
    width: 100%;
  }
`;

const HomeSection2 = () => {
  return (
    <SectionContainer backgroundColor="#FFFFFF">
      <SectionInnerContainer>
        <Section>
          <ImageSection>
            <img src={mockImg} alt="Mock Image" />
          </ImageSection>
          <TextSection>
            <h2>스타일을 기록해보세요</h2>
            <h3>
              오늘 내 스타일이 마음에 든다면 기록해 보세요.
              <br />
              꾸준히 기록한 만큼,
              <br />
              내게 어떤 스타일이 어울리는지 알 수 있습니다.
              <br />
              여러분의 멋진 하루를 추억으로 남기는 것은 덤이죠.
            </h3>
          </TextSection>
        </Section>
      </SectionInnerContainer>
    </SectionContainer>
  );
};

export default HomeSection2;
