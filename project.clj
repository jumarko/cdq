(defproject cyberdungeonquest "alpha 3-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.macro "0.1.2"]
                 [org.lwjgl.lwjgl/lwjgl          "2.9.3"]
                 ;; TODO: classifier together with native-prefix should work
                 ;; but for some reason the .dll files are always copied to target/native directory instead of
                 ;; Mac OS X libraries
                 ;; As a workaround, "java.library.path" is specified manually via :jvm-opts
                 ;; However, you need to download and place to native libs to the "<project_root>/native" directory
                 ;[org.lwjgl.lwjgl/lwjgl-platform "2.9.3" :classifier "natives-osx" :native-prefix ""]
                 [com.nothingtofind/slick2d "customized-0.1.0-SNAPSHOT"]
                 [grid2d "0.1.0-SNAPSHOT"]]
  ;; doesn't work
  ;;:native-dependencies [[lwjgl "2.9.3"]]
  ;; => need to use jvm-opts
  :java-source-paths ["src"]
  :aot [engine.render] ; read-string of Animation record
  :main game.start
  :uberjar-name "cdq_3.jar"
  :omit-source true
  :manifest {"Launcher-Main-Class" "game.start"
             "SplashScreen-Image" "splash.gif"
             "Launcher-VM-Args" "-Xms256m -Xmx256m"}
  :jvm-opts ["-Xms256m"
             "-Xmx256m"
             "-Dvisualvm.display.name=CDQ"
             "-Djava.library.path=/Users/jumar/workspace/clojure/games/cdq/native"]
  :profiles {:uberjar {:aot [game.starter game.start]
                       :main game.starter}}
  :aliases {"build" ["do" "clean" "uberjar"]})

; :main mapgen.test
;
; TODO: set correct natives for OSX, linux

