import styled from 'styled-components';

type RoundButtonProps = {
  color?: string;
  hoverColor?: string;
  backgroundColor?: string;
  size?: 'normal' | 'large';
  fontWeight?: string | number;
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
  font-weight: ${({ fontWeight }) => (fontWeight ? fontWeight : 400)};
  min-width: 70px;
  color: ${({ color }) => (color ? color : '#ffffff')};
  background-color: ${({ backgroundColor }) =>
    backgroundColor ? backgroundColor : '#fff'};
  border-radius: 45px;
  border: 0;
  outline: 0;

  &:hover {
    color: ${({ hoverColor }) => (hoverColor ? hoverColor : '#fff')};
    cursor: pointer;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.21);
    transition-duration: 0.2s;
    transition-timing-function: ease-in-out;
  }
`;
