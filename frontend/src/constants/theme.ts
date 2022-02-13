const customMediaQuery = (maxWidth: number): string =>
  `@media (min-width: ${maxWidth}px)`;

export const mediaSize = {
  mobile: 576,
  tablet: 768,
  desktop: 1440,
};

export const media = {
  custom: customMediaQuery,
  mobile: customMediaQuery(mediaSize.mobile),
  tablet: customMediaQuery(mediaSize.tablet),
  desktop: customMediaQuery(mediaSize.desktop),
};
