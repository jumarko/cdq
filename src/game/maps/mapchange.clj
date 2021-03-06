(ns game.maps.mapchange  ; TODO just change
  (:use
    utils.core
    game.utils.msg-to-player
    (game.components core body)
    game.components.skills.core
    game.maps.data
    [game.player.session-data :only (save-game)]))

(def- queued (atom nil))

(defn queue-map-change
  "for calling @ update-components loop."
  [position new-map-file save-game?]
  (when-not (or
              @queued
              ; teleport skill in new map can crash game with skill-use-tileposi of old-map
              (is-attacking? (get-component player-body :skillmanager)))
    (reset! queued [new-map-file position save-game?])))

(defn change-map
  "Never call while updating components because then components of one map suddenly operate on the current-map-data of another map!
  Use queue-map-change instead."
  ([new-map new-posi]
    (change-map new-map new-posi false))
  ([new-map new-posi save-game?]
    (set-map! new-map)
    (teleport player-body new-posi)
    (when save-game?
      (save-game)
      (show-msg-to-player  (get-pretty-name new-map) "\nSaved progress..."))))

(defn check-change-map []
  (when-let [data @queued]
    (reset! queued nil)
    (apply change-map data)))


