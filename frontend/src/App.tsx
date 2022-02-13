import React from 'react';
import './reset.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Home from '@pages/Home';
import { Container } from '@components/Container/Container.styled';

const App = () => {
  return (
    <div className="App">
      <BrowserRouter>
        <Container>
          <Routes>
            <Route path="/" element={<Home />} />
          </Routes>
        </Container>
      </BrowserRouter>
    </div>
  );
};

export default App;
