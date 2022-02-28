import styled from 'styled-components';
import { media, mediaSize } from '@constants/theme';

type SectionContainerProps = {
  backgroundColor?: string;
};

export const Container = styled.div`
  width: 100vw;
`;

export const SectionContainer = styled.div<SectionContainerProps>`
  position: relative;
  display: flex;
  justify-content: center;
  width: 100%;
  ${({ backgroundColor }) =>
    backgroundColor ? `background-color: ${backgroundColor}` : ''}
`;

export const SectionInnerContainer = styled.div`
  width: 95vw;
  z-index: 2;

  ${media.tablet} {
    width: 80vw;
  }

  ${media.desktop} {
    width: 80vw;
    max-width: ${mediaSize.desktop}px;
  }
`;
