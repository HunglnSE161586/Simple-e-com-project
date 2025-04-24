import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';  
import App from './App.tsx';
import 'bootstrap/dist/js/bootstrap.bundle.min';  // Bootstrap JS (bundled with Popper.js)
import 'bootstrap/dist/css/bootstrap.min.css';   // Bootstrap CSS

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
  </StrictMode>
);
