import styled from 'styled-components';

type RoundButtonProps = {
  color?: string;
  backgroundColor?: string;
  size?: 'normal' | 'large';
};

export const RoundButtonStyles = styled.button<RoundButtonProps>`
  padding: ${({ size }) => {
    switch (size) {
      case 'normal':
        return '7px 16px';
      case 'large':
        return '16px 32px';
      default:
        return '7px 16px';
    }
  }};
  font-size: 14px;
  min-width: 70px;
  color: ${({ color }) => (color ? color : '#fff')};
  background-color: ${({ backgroundColor }) =>
    backgroundColor ? backgroundColor : '#fff'};
  border-radius: 45px;
  border: 0;
  outline: 0;
`;
