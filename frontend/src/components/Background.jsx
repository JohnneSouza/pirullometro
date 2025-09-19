import { useEffect, useState } from "react";

export const Background = () => {
    const [fireflies, setFireflies] = useState([]);

    useEffect(() => {
        // generateFireflies();
        const handleResize = () => {
            generateFireflies();
        };

        window.addEventListener("resize", handleResize);

        return () => window.removeEventListener("resize", handleResize);
    }, []);

    const generateFireflies = () => {
        const numberOfFireflies = Math.floor(
            (window.innerWidth * window.innerHeight) / 50000
        );

        const newFireflies = [];

        for (let i = 0; i < numberOfFireflies; i++) {
            newFireflies.push({
                id: i,
                size: Math.random() * 1 + 2,
                x: Math.random() * 100,
                y: Math.random() * 100,
                glowDuration: Math.random() * 2 + 2, // 2-4s
                glowDelay: Math.random() * 4,
                moveDuration: Math.random() * 8 + 8, // 8-16s
                moveDelay: Math.random() * 8,
            });
        }

        setFireflies(newFireflies);
    };

    return (
        <div className="fixed inset-0 overflow-hidden pointer-events-none z-0">
            {fireflies.map(firefly => (
                <div
                    key={firefly.id}
                    className="firefly"
                    style={{
                        width: `${firefly.size}px`,
                        height: `${firefly.size}px`,
                        left: `${firefly.x}%`,
                        top: `${firefly.y}%`,
                        animationDuration: `${firefly.glowDuration}s, ${firefly.moveDuration}s`,
                        animationDelay: `${firefly.glowDelay}s, ${firefly.moveDelay}s`,
                        animationName: "firefly-glow, firefly-move",
                    }}
                />
            ))}
        </div>
    );
};
