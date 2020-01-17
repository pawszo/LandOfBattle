package com.pawszo.keyboardking.dev.config;

import com.pawszo.keyboardking.dev.dto.CastleDefenceScoreDTO;
import com.pawszo.keyboardking.dev.dto.CreatePrivilegeDTO;
import com.pawszo.keyboardking.dev.dto.CreateWordSetDTO;
import com.pawszo.keyboardking.dev.dto.TypeOShooterScoreDTO;
import com.pawszo.keyboardking.dev.mapper.PrivilegeMapper;
import com.pawszo.keyboardking.dev.mapper.ScoreMapper;
import com.pawszo.keyboardking.dev.mapper.WordMapper;
import com.pawszo.keyboardking.dev.model.Privilege;
import com.pawszo.keyboardking.dev.model.Role;
import com.pawszo.keyboardking.dev.model.User;
import com.pawszo.keyboardking.dev.repository.PrivilegeRepository;
import com.pawszo.keyboardking.dev.repository.RoleRepository;
import com.pawszo.keyboardking.dev.repository.ScoreRepository;
import com.pawszo.keyboardking.dev.repository.UserRepository;
import com.pawszo.keyboardking.dev.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PrivilegeMapper privilegeMapper;

    @Autowired
    private WordService wordService;
    @Autowired
    private WordMapper wordMapper;

    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private ScoreMapper scoreMapper;


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup)
            return;
        Privilege readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        Privilege adminPrivilege
                = createPrivilegeIfNotFound("ADMIN_PRIVILEGE");

        List<Privilege> adminRole = Arrays.asList(readPrivilege, writePrivilege, adminPrivilege);
        List<Privilege> userRole = Arrays.asList(readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminRole);
        createRoleIfNotFound("ROLE_USER", userRole);

        Role adminR = roleRepository.getByName("ROLE_ADMIN");
        Role userR = roleRepository.getByName("ROLE_USER");
        User user = new User();
        user.setNickname("admin");
        user.setPassword(passwordEncoder.encode("wiedzmin3"));
        user.setEmail("paulszoltysek@gmail.com");
        user.setRoles(Arrays.asList(adminR, userR));
        user.setState("active");
        userRepository.save(user);
        createNecessaryWordSets();


        alreadySetup = true;
    }

    @Transactional
    public Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name).orElse(null);
        if (privilege == null) {
            privilege = privilegeMapper.createModel(new CreatePrivilegeDTO(name));
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    public Role createRoleIfNotFound(String name, List<Privilege> privileges) {

        Role role = roleRepository.findByName(name).orElse(null);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

    public void createNecessaryWordSets() {
        wordService.addWords(new CreateWordSetDTO("If you could not find the words you were looking for please submit feedback or leave a comment below. Let me know what word list you could not find and be sure to get it fixed up for you", "EN"));
        wordService.addWords(new CreateWordSetDTO("bytes that express a binary integer that is not just a character is a binary file not plain text by even the loosest common usages. Put another way, translating a plain text file to a character encoding that uses entirely different number to represent characters", "EN"));
        wordService.addWords(new CreateWordSetDTO("was not so useful in England, and the accented characters used in Spanish, French, German, and many other languages were entirely unavailable in", "EN"));
        wordService.addWords(new CreateWordSetDTO("tower horse table insert target pilot widow window drink letter inform outer Many other organisations developed variations on these, and for many years Windows and Macintosh computers used incompatible variations", "EN"));
        wordService.addWords(new CreateWordSetDTO("cross comparison sniff happen preserve cattle pray defiant cup zesty appear slippery chin meat nondescript challenge club battle license important government crown next lewd prevent husky license uptight round race innocent thaw aquatic ignorant rock aware previous letters lighten synonymous camera analyse smoke tip fat seashore sign damaged side moan", "EN"));
        wordService.addWords(new CreateWordSetDTO("absolute abstract academic accepted accident accuracy accurate achieved acquired activity actually addition adequate adjacent adjusted advanced advisory advocate affected aircraft alliance although aluminum analysis announce anything anywhere apparent appendix approach approval argument artistic assembly assuming athletic attached attitude attorney audience autonomy aviation bachelor bacteria baseball bathroom becoming benjamin birthday", "EN"));
        wordService.addWords(new CreateWordSetDTO("jak tak rak brak lat tym ten rym dym worek wtorek pole dola gol mur nurt brud typ pot lot dryg mama tata syn beta gamma dama mord beza karp derka kim byt polka drogo rzekomo istotnie dlaczego czemu temu tej owa owej ratrak wiatr wiatrak kolej tory komputer klawiatura instrument algorytm wampir potwory ligawa piersi szyja udo policzek palec obojczyk stopa spodnie", "PL"));
        wordService.addWords(new CreateWordSetDTO("wycieraczka szyba maszyna pomniejszy mniejszy ogrom pogoda porzekadlo prawy lewarek istnieje wpisz anagram szukaj szukamy modlimy dedykowany pozostale zapoznali pognali pobili obarczyli okablowanie komfortowo korzystny kompetentny tygodniowy dzienny nocny niecodzienny niezdarny niepoliczalny niewyobrazalny klocki zabawka tworzone dopasowany dopasowani wrzody pergamin macierz macierzysta", "PL"));
        wordService.addWords(new CreateWordSetDTO("wie so Krebs Mangel an Jahren dieser Reim Sack Dienstag unten Ziel Feld aktuelle Wand Schweiss Typ Flug Typ Nippel Mama Papa Sohn Beta Gamma Lady Mord Baiser Karpfen Decke wird angeblich der Tat warum dieser dieser Wind Ratte Windmuehle Computertastatur instrumentalgorithmus vampir monster ligave brust hals oberschenkel wange finger schlusselbein fuss hose", "DE"));
        wordService.addWords(new CreateWordSetDTO("Scheibenwischermaschine kleinere enorme Wetter richtige Verzierung existiert enter anagram search wir suchen gewidmet gewidmet andere vertraut gehetzt geschlagen Verkabelung bequem kompetent Nacht aus dem normalen ungeschickten kompromisslosen Spielzeugblöcke erstellt passenden Pergament nach Hause ", "DE"));
        wordService.addWords(new CreateWordSetDTO("kur Ich mag nostalgische alte Worte gern Erinnerungen in uns hervorrufen Man spricht nicht umsonst von einem Wort kann diese Begriffe auch Kraft Herzenswunsch Lebenslust Lebenslust Pusteblume Purzelbaum Saumseligkeit Schluckspecht Sommernachtstraum Vorfreude ich dich mein dein diese uns wir dir kann sein bein beiss kugel bier vatter bruder schwester sonn kein tragische malen bauen trink", "DE"));
        wordService.addWords(new CreateWordSetDTO("frontrute vindusvisker maskin mindre stoerrelse enormt vaer riktig pryd eksisterer inn anagram soek vi ser etter be dedikerte andre kjente stormet slaatt ledninger komfortabelt gunstig kompetent ukentlig dagtid natt utenom det vanlige kloenete utellelige kompromissløse leketoeyblokker opprettet matchende magesaar pergament hjemmematrise", "NO"));
        wordService.addWords(new CreateWordSetDTO("absolutt abstrakt akademisk akseptert ulykke noeyaktighet noeyaktig faktisk oppnaadd tilstrekkelig tilstoetende justert avansert raadgivende advokat beroert luftfartoey allianse selv om aluminium analyse kunngjoere noe hvor som helst aapenbar vedlegg tilnaerming godkjenning argument kunstnerisk forsamling forutsatt atletisk vedlagt holdning advokat autonom luftfart bachelor bakterier baseball bad blir benjamin bursdag", "NO"));
        wordService.addWords(new CreateWordSetDTO("jeg deg ham henne din min hans hennes villmark spise drikke ses prate tale ikke paaleg smoer broed andre annet ulykke staa opp det den der her herfra derfra grunn testatur livet huset bil bilen butikk kiosk lampe maskin skip troett spis loep bett bord stol leder vann hva klokka dyrt billig mulig trolig jobb sykkel muskel ting alt kalt inne ned lang kniv gaffel satt graven dal doer steng aapen lyst lys stroemm", "NO"));
    }


    public void createSomeScores() {
        scoreRepository.save(scoreMapper.createModel(new TypeOShooterScoreDTO("200", "120", "worek", "PL", "pawel", "Type'o'Shooter")));
        scoreRepository.save(scoreMapper.createModel(new TypeOShooterScoreDTO("98", "43", "window", "EN", "adam", "Type'o'Shooter")));
        scoreRepository.save(scoreMapper.createModel(new CastleDefenceScoreDTO("98", "43", "4", "adam", "Castle-defence")));
        scoreRepository.save(scoreMapper.createModel(new CastleDefenceScoreDTO("98", "43", "6", "pawel", "Castle-defence")));


    }


}
