import { Background } from "@/components/Background";
import { useNavigate } from "react-router-dom";
import { ThemeToggle } from "../components/ThemeToggle";

export const NotFound = () => {
  const navigate = useNavigate();

  return (
  <div className="min-h-screen text-foreground overflow-x-hidden flex items-center justify-center">
  <Background />
      <ThemeToggle />

      <div className="container max-w-4xl mx-auto text-center z-10 flex flex-col items-center justify-center min-h-screen">
        <div className="space-y-6">
          <h1 className="text-4xl md:text-6xl font-bold tracking-tight">
            <span className="opacity-0 animate-fade-in"> </span>
            <span className="text-primary opacity-0 animate-fade-in-delay-1">
              {" "}
              404
            </span>
          </h1>

          <p className="text-lg md:text-xl text-muted-foreground max-2-2xl mx-auto opacity-0 animate-fade-in-delay-3">
            Ops, parece que você se perdeu por aqui.
          </p>

          <div className="pt-4 opacity-0 animate-fade-in-delay-4">
            <button
              className="cosmic-button"
              onClick={() => navigate(-1)}
            >
              Voltar
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};