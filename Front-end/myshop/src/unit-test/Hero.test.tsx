import { render,screen } from "@testing-library/react";
import HeroSection from "../components/Hero";
import "@testing-library/jest-dom"
test('displays the hero section title', () => {
  render(<HeroSection/>);
  
  // Check if the h1 with the specific text is in the document
  const heading = screen.getByRole('heading', { name: /Welcome to Millennium Mart/i });
  
  expect(heading).toBeInTheDocument();
});