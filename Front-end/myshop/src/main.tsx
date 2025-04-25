import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';  
import 'bootstrap/dist/js/bootstrap.bundle.min';  // Bootstrap JS (bundled with Popper.js)
import 'bootstrap/dist/css/bootstrap.min.css';   // Bootstrap CSS
import AppWrapper from './App.tsx';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <AppWrapper />
  </StrictMode>
);
