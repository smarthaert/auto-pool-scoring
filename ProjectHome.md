In this project, we propose the creation of an automatic scoring system for pool/billiards games that would be useful for competitive match settings. Competitive pool leagues, such as the American Poolplayers Association (APA) and TAP league, are recreational team-based leagues that involve complex scoring systems that are cumbersome for the scorer. During a match, numerous statistics must be accurately scored, and may include: number of pocketed balls, dead balls (balls made during a fault), missed balls, timeouts, defense shots, balls made on the break, balls left on the table, etc. Scoring may actually hinder game progression if players shoot quickly and must wait for scorers to catch-up. The goal of the automatic pool/billiard scoring system we propose is to accurately score aforementioned statistics, with an intuitive menu system for human correction when the system incorrectly marks a score. The development of a prototype for scoring will be based upon “straight pool” games, which involves the least contextual decision points of the three most popular pool games (straight pool, 8-ball, and 9-ball).

There are four rows for each player, with one player assigned to each main column.  The names entered by each player in the login/setup process will be used for display in the main screen.  The Rack Points and Balls this Rack rows are used to indicate the points that have been earned by each player for the current rack.  Many straight pool players isolate the points earned in the current rack before aggregating total points (this assumes a traditional bead style counting scheme) in order to assure that all 15 balls have been correctly scored.  This design leverages that common practice to help provide visibility of system state to the users, and is explained in detail below.  The Total row is used to provide a combined score, which sums all points made in all previous racks as well as all points that have been earned in the current rack.  Scores shown in this row represent the total current score by an individual player.  The Goal row is used to indicate the number of points that must be made in order for a player to win the game.  Both players will typically have the same goal during a match, but may differ if handicaps are used.  The Goal row will be displayed for both handicapped and non-handicapped games in order to facilitate a consistent model for both operational modes.  At the bottom, there are four buttons: QUIT, STATS, REPLAY, and NEXT RACK.  The QUIT button offers the same functionality as described in the initial setup/login phase.  The STATS button will allow players to review game play statistics, which may be viewed at any point during or after the game.  The REPLAY button is used to take users to a screen to replay the last shot as recorded by the system.  Finally, the NEXT RACK button is used to clear data from the Rack Points and Balls this Rack rows.  After both players agree upon the scoring data and all 15 balls have been accounted for, the players' scores within the total cells will be accurate.  Because re-racking the balls already serves to naturally delay game play, the users' action of pressing this button should have minimal impact.  The system could also detect when a re-rack is occurring, and perform the NEXT RACK action autonomously.  This interface also includes a player turn indicator that consists of an arrow and rectangle that encircles the player currently shooting at the table.