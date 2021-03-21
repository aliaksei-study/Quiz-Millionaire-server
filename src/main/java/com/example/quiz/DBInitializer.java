package com.example.quiz;

import com.example.quiz.controller.v1.response.TranslatedQuestionResponse;
import com.example.quiz.dto.TranslatedCategoryDto;
import com.example.quiz.dto.TranslatedQuestionDto;
import com.example.quiz.exception.CategoryNotFoundException;
import com.example.quiz.mapper.Mapper;
import com.example.quiz.model.*;
import com.example.quiz.model.enumeration.Difficulty;
import com.example.quiz.model.enumeration.Role;
import com.example.quiz.repository.CategoryRepository;
import com.example.quiz.repository.LanguageRepository;
import com.example.quiz.repository.PlayerRepository;
import com.example.quiz.repository.QuestionRepository;
import com.example.quiz.service.ICategoryService;
import com.example.quiz.service.IQuestionService;
import org.apache.tomcat.jni.Local;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConditionalOnProperty(name = "app.db-init", havingValue = "true")
public class DBInitializer implements CommandLineRunner {
    private final PlayerRepository playerRepository;
    private final LanguageRepository languageRepository;
    private final PasswordEncoder passwordEncoder;
    private final IQuestionService questionService;
    private final ICategoryService categoryService;

    public DBInitializer(CategoryRepository categoryRepository, QuestionRepository questionRepository,
                         PlayerRepository playerRepository, LanguageRepository languageRepository,
                         PasswordEncoder passwordEncoder, IQuestionService questionService,
                         ICategoryService categoryService) {
        this.playerRepository = playerRepository;
        this.languageRepository = languageRepository;
        this.passwordEncoder = passwordEncoder;
        this.questionService = questionService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) {
        Player player = new Player("alexbeljak99@gmail.com", passwordEncoder.encode("123456"), Role.ADMIN);
        playerRepository.save(player);

        Language russianLanguage = new Language("Russian", "ru");
        languageRepository.save(russianLanguage);

        Language englishLanguage = new Language("English", "en-US");
        languageRepository.save(englishLanguage);

        Category category = new Category(List.of(new LocalizedCategory("History", englishLanguage),
                new LocalizedCategory("История", russianLanguage)));
        categoryService.saveCategory(Mapper.map(category, TranslatedCategoryDto.class));
        List<Answer> answerList = new ArrayList<>();
        try {
            saveQuestion(new Answer(List.of(new LocalizedAnswer("1941", russianLanguage), new LocalizedAnswer("1941", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("1938", russianLanguage), new LocalizedAnswer("1938", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("1940", russianLanguage), new LocalizedAnswer("1940", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("1939", russianLanguage), new LocalizedAnswer("1939", englishLanguage)), true),
                    answerList, "В каком году началасть Вторая мировая война?",
                    "In what year did World War II begin?", russianLanguage, englishLanguage, null, false, Difficulty.EASY, category, (long)1);

            saveQuestion(new Answer(List.of(new LocalizedAnswer("1 сентября 1939", russianLanguage), new LocalizedAnswer("September 1, 1939", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("23 июня 1941", russianLanguage), new LocalizedAnswer("June 23, 1941", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("22 июня 1941", russianLanguage), new LocalizedAnswer("June 22, 1941", englishLanguage)), true),
                    new Answer(List.of(new LocalizedAnswer("1 сентября 1941", russianLanguage), new LocalizedAnswer("September 1, 1939", englishLanguage)), false),
                    answerList, "Дата начала Великой Отечественной Войны?", "Date of the beginning of the Great Patriotic War?",
                    russianLanguage, englishLanguage, null, false, Difficulty.EASY, category, (long)1);

            saveQuestion(new Answer(List.of(new LocalizedAnswer("1918", russianLanguage), new LocalizedAnswer("1918", englishLanguage)), true),
                    new Answer(List.of(new LocalizedAnswer("1945", russianLanguage), new LocalizedAnswer("1945", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("1917", russianLanguage), new LocalizedAnswer("1917", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("1944", russianLanguage), new LocalizedAnswer("1944", englishLanguage)), false),
                    answerList, "В каком году закончилась Первая мировая война?", "In what year did the First World War end?",
                    russianLanguage, englishLanguage, null, false, Difficulty.EASY, category, (long)1);

            Category literatureCategory = new Category(List.of(new LocalizedCategory("Literature", englishLanguage),
                    new LocalizedCategory("Литература", russianLanguage)));
            categoryService.saveCategory(Mapper.map(literatureCategory, TranslatedCategoryDto.class));
            saveQuestion(new Answer(List.of(new LocalizedAnswer("Александр Пушкин", russianLanguage), new LocalizedAnswer("Alexander Pushkin", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Николай Гоголь", russianLanguage), new LocalizedAnswer("Nicolai Gogol", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Лев Толстой", russianLanguage), new LocalizedAnswer("Lev Tolstoy", englishLanguage)), true),
                    new Answer(List.of(new LocalizedAnswer("Иван Тургенев", russianLanguage), new LocalizedAnswer("Ivan Turgenev", englishLanguage)), false),
                    answerList, "Автор романа Война и мир?", "Author of the novel War and Peace?",
                    russianLanguage, englishLanguage, null, false, Difficulty.EASY, literatureCategory, (long)2);

            saveQuestion(new Answer(List.of(new LocalizedAnswer("Уильям Шекспир", russianLanguage), new LocalizedAnswer("William Shakespeare", englishLanguage)), true),
                    new Answer(List.of(new LocalizedAnswer("Александр Пушкин", russianLanguage), new LocalizedAnswer("Alexander Pushkin", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Иван Тургенев", russianLanguage), new LocalizedAnswer("Ivan Turgenev", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Лев Толстой", russianLanguage), new LocalizedAnswer("Lev Tolstoy", englishLanguage)), false),
                    answerList, "Автор трагедии Ромео и Джульетта?", "The author of the tragedy Romeo and Juliet?",
                    russianLanguage, englishLanguage, null, false, Difficulty.MEDIUM, literatureCategory, (long)2);

            saveQuestion(new Answer(List.of(new LocalizedAnswer("Александр Пушкин", russianLanguage), new LocalizedAnswer("Alexander Pushkin", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Максим Горький", russianLanguage), new LocalizedAnswer("Maksim Gorky", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Иван Тургенев", russianLanguage), new LocalizedAnswer("Ivan Turgenev", englishLanguage)), true),
                    new Answer(List.of(new LocalizedAnswer("Лев Толстой", russianLanguage), new LocalizedAnswer("Lev Tolstoy", englishLanguage)), false),
                    answerList, "Автор рассказа Муму?", "The author of the story Mumu?",
                    russianLanguage, englishLanguage, null, false, Difficulty.MEDIUM, literatureCategory, (long)2);

            saveQuestion(new Answer(List.of(new LocalizedAnswer("Абудаби", russianLanguage), new LocalizedAnswer("Abu Dhabi", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Валенсия", russianLanguage), new LocalizedAnswer("Valencia", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Ванкувер", russianLanguage), new LocalizedAnswer("Vancouver", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Стратфорд-на-Эйвоне", russianLanguage), new LocalizedAnswer("Stratford-upon-Avon", englishLanguage)), true),
                    answerList, "В каком городе родился Уильям Шекспир?", "In which city was William Shakespeare born?",
                    russianLanguage, englishLanguage, null, false, Difficulty.HARD, literatureCategory, (long)2);

            Category moneyCategory = new Category(List.of(new LocalizedCategory("Money", englishLanguage), new LocalizedCategory("Деньги", russianLanguage)));
            categoryService.saveCategory(Mapper.map(moneyCategory, TranslatedCategoryDto.class));
            saveQuestion(new Answer(List.of(new LocalizedAnswer("Доллар", russianLanguage), new LocalizedAnswer("Dollar", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Евро", russianLanguage), new LocalizedAnswer("Euro", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Фунт стерлингов", russianLanguage), new LocalizedAnswer("Pound Sterling", englishLanguage)), true),
                    new Answer(List.of(new LocalizedAnswer("Британский Шиллинг", russianLanguage), new LocalizedAnswer("British shilling", englishLanguage)), false),
                    answerList, "Валюта Великобритании?", "UK currency?",
                    russianLanguage, englishLanguage, null, false, Difficulty.MEDIUM, moneyCategory, (long)3);

            saveQuestion(new Answer(List.of(new LocalizedAnswer("Евро", russianLanguage), new LocalizedAnswer("Euro", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Швейцарский франк", russianLanguage), new LocalizedAnswer("Swedish frank", englishLanguage)), true),
                    new Answer(List.of(new LocalizedAnswer("Доллар", russianLanguage), new LocalizedAnswer("Dollar", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Фунт стерлингов", russianLanguage), new LocalizedAnswer("Pound Sterling", englishLanguage)), false),
                    answerList, "Валюта Швейцарии?", "Sweden currency?",
                    russianLanguage, englishLanguage, null, false, Difficulty.MEDIUM, moneyCategory, (long)3);

            Category secondCategory = new Category(List.of(new LocalizedCategory("Geography", englishLanguage), new LocalizedCategory("География", russianLanguage)));
            categoryService.saveCategory(Mapper.map(secondCategory, TranslatedCategoryDto.class));
            saveQuestion(new Answer(List.of(new LocalizedAnswer("Кирибати", russianLanguage), new LocalizedAnswer("Kiribati", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Палау", russianLanguage), new LocalizedAnswer("Palau", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Науру", russianLanguage), new LocalizedAnswer("Nauru", englishLanguage)), true),
                    new Answer(List.of(new LocalizedAnswer("Маршалловы острова", russianLanguage), new LocalizedAnswer("Marshall Islands", englishLanguage)), false),
                    answerList, "На флаге какой страны Океании изображена линия, символизирующая экватор?", "Which country in Oceania has a line symbolizing the equator on its flag?",
                    russianLanguage, englishLanguage, null, false, Difficulty.NIGHTMARE, secondCategory, (long)4);
            saveQuestion(new Answer(List.of(new LocalizedAnswer("Кирибати", russianLanguage), new LocalizedAnswer("Kiribati", englishLanguage)), true),
                    new Answer(List.of(new LocalizedAnswer("Палау", russianLanguage), new LocalizedAnswer("Palau", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Науру", russianLanguage), new LocalizedAnswer("Nauru", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Маршалловы острова", russianLanguage), new LocalizedAnswer("Marshall Islands", englishLanguage)), false),
                    answerList, "Флаг какой страны изображен на картинке?", "Which country flag is shown in the picture?",
                    russianLanguage, englishLanguage, "https://res.cloudinary.com/dsnsf4ukx/image/upload/v1603614834/Kiribati_uk6wy8.png",
                    false, Difficulty.NIGHTMARE, secondCategory, (long)4);
            saveQuestion(new Answer(List.of(new LocalizedAnswer("Италия", russianLanguage), new LocalizedAnswer("Italy", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Франция", russianLanguage), new LocalizedAnswer("France", englishLanguage)), true),
                    new Answer(List.of(new LocalizedAnswer("Испания", russianLanguage), new LocalizedAnswer("Spain", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Россия", russianLanguage), new LocalizedAnswer("Russia", englishLanguage)), false),
                    answerList, "Флаг какой страны изображен на картинке?", "Which country flag is shown in the picture?",
                    russianLanguage, englishLanguage, "https://res.cloudinary.com/dsnsf4ukx/image/upload/v1603615118/France_oyifgk.jpg",
                    false, Difficulty.MEDIUM, secondCategory, (long)4);
            saveQuestion(new Answer(List.of(new LocalizedAnswer("Италия", russianLanguage), new LocalizedAnswer("Italy", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Франция", russianLanguage), new LocalizedAnswer("France", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Босния и Герцеговина", russianLanguage), new LocalizedAnswer("Bosnia and Herzegovina", englishLanguage)), true),
                    new Answer(List.of(new LocalizedAnswer("Болгария", russianLanguage), new LocalizedAnswer("Bulgaria", englishLanguage)), false),
                    answerList, "Флаг какой страны изображен на картинке?", "Which country flag is shown in the picture?",
                    russianLanguage, englishLanguage, "https://res.cloudinary.com/dsnsf4ukx/image/upload/v1603615377/BosniyaAndHercegovina_ubuzls.png",
                    false, Difficulty.HARD, secondCategory, (long)4);
            saveQuestion(new Answer(List.of(new LocalizedAnswer("Северного Ледовитого", russianLanguage), new LocalizedAnswer("Arctic", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Атлантического", russianLanguage), new LocalizedAnswer("Atlantic", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Индийского", russianLanguage), new LocalizedAnswer("Indian", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Южного", russianLanguage), new LocalizedAnswer("Southern", englishLanguage)), true),
                    answerList, "Воды какого океана не омывают Евразию?", "What ocean waters do not wash Eurasia?",
                    russianLanguage, englishLanguage, null, false,
                    Difficulty.EASY, secondCategory, (long)4);
            saveQuestion(new Answer(List.of(new LocalizedAnswer("Нил", russianLanguage), new LocalizedAnswer("Nile", englishLanguage)), true),
                    new Answer(List.of(new LocalizedAnswer("Амазонка", russianLanguage), new LocalizedAnswer("Amazon", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Янцзы", russianLanguage), new LocalizedAnswer("Yangtze", englishLanguage)), false),
                    new Answer(List.of(new LocalizedAnswer("Хуанхэ", russianLanguage), new LocalizedAnswer("Huanghe", englishLanguage)), false),
                    answerList, "Самая длинная река в мире?", "The longest river in the world?",
                    russianLanguage, englishLanguage, null, false, Difficulty.HARD, secondCategory, (long)4);
        } catch(CategoryNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void saveQuestion(Answer firstAnswer, Answer secondAnswer, Answer thirdAnswer, Answer fourthAnswer,
                             List<Answer> answerList, String russianText, String englishText, Language firstLanguage, Language secondLanguage, String imagePath, Boolean isTemporal,
                             Difficulty difficulty, Category category, Long categoryId) throws CategoryNotFoundException {
        answerList.clear();
        answerList.add(firstAnswer);
        answerList.add(secondAnswer);
        answerList.add(thirdAnswer);
        answerList.add(fourthAnswer);
        Question question = new Question(List.of(new LocalizedQuestion(russianText, firstLanguage),
                new LocalizedQuestion(englishText, secondLanguage)), imagePath,
                isTemporal, difficulty, category, answerList);
        TranslatedQuestionDto questionDto = Mapper.map(question, TranslatedQuestionDto.class);
        questionDto.setCategory(categoryId);
        questionService.saveQuestion(questionDto);
    }
}
