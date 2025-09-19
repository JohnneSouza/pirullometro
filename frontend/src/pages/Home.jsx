import { Navbar } from "../components/Navbar";
import { ThemeToggle } from "../components/ThemeToggle";
import { Background } from "@/components/Background";
import { DurationSection } from "../components/DurationSection";
import { AboutSection } from "../components/AboutSection";
import { SkillsSection } from "../components/SkillsSection";
import { ProjectsSection } from "../components/ProjectsSection";
import { ContactSection } from "../components/ContactSection";
import { Footer } from "../components/Footer";

export const Home = () => {
  return (
  <div className="min-h-screen text-foreground overflow-x-hidden">
      {/* Theme Toggle */}
      <ThemeToggle />
      {/* Background Effects */}
  <Background />

      {/* Navbar */}
      <Navbar />
      {/* Main Content */}
      <main>
        <DurationSection />
        <ContactSection /> 
        {/* <AboutSection />
        <SkillsSection />
        <ProjectsSection />*/}
      </main>

      {/* Footer */}
      {/* <Footer /> */}
    </div>
  );
};