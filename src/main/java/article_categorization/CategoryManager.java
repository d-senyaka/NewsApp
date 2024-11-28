package article_categorization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryManager {

    private static final Map<String, List<String>> categoryKeywords = new HashMap<>();

    static {
        // Business Keywords
        categoryKeywords.put("Business", List.of(
                "investment", "market", "stocks", "economy", "revenue", "profit", "loss",
                "funding", "capital", "acquisition", "merger", "IPO", "growth", "finance",
                "corporate", "strategy", "business", "expansion", "trade", "earnings",
                "billion", "million", "CEO", "shareholder", "NASDAQ", "S&P 500",
                "Dow Jones", "inflation", "federal", "interest", "banking", "valuation",
                "dividend", "quarterly", "Wall Street", "entrepreneur", "venture",
                "startup", "retail", "consumer", "sales", "supply chain", "forecast",
                "innovation", "Tesla", "Nvidia", "partnership", "recession", "economic",
                "merchandising", "audit", "taxation", "globalization", "commodity",
                "debt", "investment banking", "hedge fund", "currency", "forex",
                "shareholding", "equity", "liquidity", "cash flow", "financial report",
                "accounting", "balance sheet", "corporate governance", "marketing",
                "digital transformation", "monetary policy", "tariff", "exports",
                "imports", "economic growth", "economic downturn", "profit margin",
                "competitive advantage", "logistics", "franchise", "sustainability",
                "diversification", "acquisitions", "synergy", "e-commerce", "big data"
        ));

        // Entertainment Keywords
        categoryKeywords.put("Entertainment", List.of(
                "movie", "film", "Netflix", "Hollywood", "actor", "actress", "trailer",
                "premiere", "blockbuster", "series", "TV", "streaming", "concert",
                "music", "singer", "album", "award", "festival", "performance", "show",
                "comedy", "drama", "thriller", "romance", "documentary", "Oscars",
                "Golden Globes", "Emmys", "Broadway", "theater", "tour", "pop culture",
                "celebrity", "gossip", "scandal", "viral", "reality show", "Bollywood",
                "Disney", "Pixar", "Marvel", "DC", "cinematography", "critics",
                "entertainment", "stand-up", "box office", "trending", "sitcom",
                "animation", "short film", "web series", "independent film", "director",
                "producer", "screenplay", "cinema", "film festival", "soundtrack",
                "trending songs", "MTV", "Grammy Awards", "fashion", "pop star",
                "Broadway play", "exclusive interview", "fan following", "streaming platform"
        ));

        // General Keywords
        categoryKeywords.put("General", List.of(
                "news", "update", "global", "world", "headline", "event", "government",
                "politics", "policy", "society", "community", "local", "national",
                "international", "media", "public", "opinion", "analysis", "commentary",
                "culture", "human interest", "awareness", "climate", "environment",
                "education", "technology", "trend", "survey", "statistics", "insight",
                "perspective", "feature", "daily", "weekly", "monthly", "press",
                "agency", "corruption", "justice", "freedom", "rights", "change",
                "initiative", "development", "Biden", "South Africa", "White House",
                "law enforcement", "nonprofit", "activism", "current affairs",
                "economic policy", "world leaders", "diplomacy", "opinion polls",
                "social justice", "poverty", "charity", "healthcare policy",
                "environmental protection", "public sector", "international relations",
                "immigration", "security", "military", "natural disasters", "humanitarian aid",
                "local elections", "referendum", "infrastructure", "civil rights"
        ));

        // Health Keywords
        categoryKeywords.put("Health", List.of(
                "health", "wellness", "fitness", "medicine", "mental health",
                "physical health", "disease", "treatment", "diagnosis", "doctor",
                "nurse", "hospital", "clinic", "symptoms", "cure", "vaccine",
                "virus", "pandemic", "COVID-19", "epidemic", "nutrition", "diet",
                "exercise", "therapy", "depression", "anxiety", "stress", "recovery",
                "immunity", "infection", "diabetes", "stroke", "heart", "brain",
                "neuroscience", "psychology", "pregnancy", "pediatrics", "public health",
                "research", "innovation", "emergency", "medication", "addiction",
                "mental illness", "first aid", "lifespan", "pharmaceutical",
                "weight loss", "obesity", "fitness plan", "cholesterol", "vitamins",
                "calories", "yoga", "health insurance", "surgery", "postpartum",
                "geriatric care", "oncology", "cardiology", "psychiatry", "ER",
                "self-care", "substance abuse", "occupational health", "biomedical"
        ));

        // Science Keywords
        categoryKeywords.put("Science", List.of(
                "science", "technology", "research", "study", "experiment",
                "discovery", "innovation", "space", "astronomy", "physics", "chemistry",
                "biology", "neuroscience", "environment", "climate", "genetics",
                "evolution", "earth", "ocean", "meteorology", "geology", "robotics",
                "AI", "machine learning", "data science", "engineering", "math",
                "scientist", "analysis", "observation", "NASA", "Mars", "moon",
                "stars", "planets", "cosmos", "telescope", "galaxy", "milky way",
                "universe", "particle", "nuclear", "fusion", "life", "biology",
                "zoology", "paleontology", "scientific theory", "quantum mechanics",
                "space exploration", "spacecraft", "biotechnology", "microbiology",
                "cosmology", "AI ethics", "bioengineering", "fieldwork", "natural phenomena"
        ));

        // Sports Keywords
        categoryKeywords.put("Sports", List.of(
                "sports", "game", "match", "tournament", "competition", "team",
                "player", "coach", "score", "win", "lose", "draw", "goal", "point",
                "league", "championship", "season", "playoff", "athlete", "record",
                "performance", "training", "stadium", "fans", "baseball", "basketball",
                "football", "soccer", "tennis", "golf", "cricket", "hockey", "swimming",
                "cycling", "track", "field", "medal", "Olympics", "world cup",
                "MLB", "NFL", "NBA", "NHL", "Patriots", "Packers", "Clemson Tigers",
                "sportsmanship", "injury", "team spirit", "game strategy", "coach interview",
                "sports commentary", "training camp", "rookie", "veteran", "team management",
                "referee", "fan zone", "stadium atmosphere", "athlete endorsement"
        ));

        // Technology Keywords
        categoryKeywords.put("Technology", List.of(
                "technology", "tech", "innovation", "software", "hardware", "AI",
                "machine learning", "artificial intelligence", "robotics", "automation",
                "cybersecurity", "cloud", "data", "analytics", "internet", "network",
                "programming", "coding", "developer", "engineer", "computer", "mobile",
                "smartphone", "app", "application", "platform", "game", "console",
                "VR", "AR", "wearable", "device", "gadget", "startup", "venture",
                "blockchain", "cryptocurrency", "bitcoin", "NVIDIA", "GeForce",
                "RTX", "PS5", "Bungie", "Blackwell", "VR headset", "smart assistant",
                "quantum computing", "IoT", "5G", "self-driving car", "biometrics"
        ));
    }

    public Map<String, List<String>> getCategoryKeywords() {
        return categoryKeywords;
    }
}
