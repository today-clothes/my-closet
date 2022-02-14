import {
  SectionContainer,
  SectionInnerContainer,
} from '@components/Container/Container.styled';
import React from 'react';
import styled from 'styled-components';
import mockImg from '@images/home/section2.png';
import { media } from '@constants/theme';

const Section = styled.section`
  display: flex;
  justify-content: space-between;
  padding: 120px 40px;

  ${media.mobile} {
    flex-direction: column-reverse;
    align-items: center;
    img {
      margin-bottom: 40px;
    }
  }
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
  ${media.mobile} {
    width: 100%;
    img {
      margin-bottom: 50px;
    }
  }
`;

const HomeSection2 = () => {
  return (
    <SectionContainer backgroundColor="#D7D2D9">
      <SectionInnerContainer>
        <Section>
          <TextSection>
            <h2>나만의 옷장을 만들어보세요</h2>
            <h3>
              원하는 분류대로 마음껏 옷장을 만들어보세요.
              <br />
              자주 입는 옷, 데이트 갈 때 입는 옷 등
              <br />
              다양한 성격의 옷장을 만들 수 있어요.
            </h3>
          </TextSection>
          <ImageSection>
            <img src={mockImg} alt="Mock Image" />
          </ImageSection>
        </Section>
      </SectionInnerContainer>
    </SectionContainer>
  );
};

export default HomeSection2;
