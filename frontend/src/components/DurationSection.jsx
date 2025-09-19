import { useEffect, useState } from "react";
import { ArrowDown } from "lucide-react";
import { apiFetch } from "../lib/apiFetch";

export const DurationSection = () => {
  const [pirullaStats, setPirullaStats] = useState({
    pirullaDuration: null,
    totalVideos: null,
    totalHours: null,
  });

  useEffect(() => {
    apiFetch("/videos/pirulla")
      .then((res) => res.json())
      .then((data) => {
        setPirullaStats({
          pirullaDuration: data.pirullaDuration ?? null,
          totalVideos: data.totalVideos ?? null,
          totalHours: data.totalHours ?? null,
        });
      })
      .catch(() => {
        setPirullaStats({ pirullaDuration: null, totalVideos: null, totalHours: null });
      });
  }, []);

  return (
    <section
      id="hero"
      className="relative min-h-screen flex flex-col items-center justify-center px-4"
    >
      <div className="container max-w-4xl mx-auto text-center z-10">
        <div className="inline-block bg-white/80 dark:bg-black/60 p-6 rounded-xl shadow-lg space-y-6 backdrop-blur-sm">
          <div className="space-y-6">
            <h1 className="text-4xl md:text-6xl font-bold tracking-tight">
              <span className="opacity-0 animate-fade-in"> 1 Pirulla = </span>
              <span className="text-primary opacity-0 animate-fade-in-delay-1">
                {pirullaStats.pirullaDuration !== null ? `${pirullaStats.pirullaDuration}` : "…"}
              </span>
            </h1>

            <p className="text-lg md:text-xl text-muted-foreground max-2-2xl mx-auto opacity-0 animate-fade-in-delay-3">
              Atualmente o canal possui {pirullaStats.totalVideos} videos, somando um total de {pirullaStats.totalHours} horas de conteúdo.
            </p>

            <RandomVideoButton />

            <p className="text-sm md:text-xl text-muted-foreground max-2-2xl mx-auto opacity-0 animate-fade-in-delay-3">
              Valores baseados na média do tempo de duração dos vídeos do canal
              oficial <a href="https://www.youtube.com/@Pirulla25" target="_blank" rel="noopener noreferrer" className="underline text-primary">@Pirulla25</a>.
            </p>

            <div className="pt-4 opacity-0 animate-fade-in-delay-4">
              {/* <a href="#projects" className="cosmic-button">
                View My Work
              </a> */}
            </div>
          </div>
        </div>
      </div>

      <div className="absolute bottom-8 left-1/2 transform -translate-x-1/2 flex flex-col items-center animate-bounce">
        {/* <span className="text-sm text-muted-foreground mb-2"> Scroll </span> */}
        {/* <ArrowDown className="h-5 w-5 text-primary" /> */}
      </div>
    </section>
  );
// Button to fetch and open a random video
function RandomVideoButton() {
  const [loading, setLoading] = useState(false);
  const handleClick = async () => {
    setLoading(true);
    try {
  const res = await apiFetch("/videos/random");
      if (!res.ok) throw new Error("Ops, algo não está certo.");
      const data = await res.json();
      if (data.videoUrl) {
        window.open(data.videoUrl, "_blank", "noopener,noreferrer");
      } else {
        alert("Vídeo não encontrado.");
      }
    } catch (e) {
      alert("Não foi possível buscar o vídeo.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <button
      className="cosmic-button w-full flex items-center justify-center gap-2 mb-4"
      onClick={handleClick}
      disabled={loading}
      type="button"
    >
      {loading ? "Carregando..." : "Que tal um vídeo?"}
    </button>
  );
}
};