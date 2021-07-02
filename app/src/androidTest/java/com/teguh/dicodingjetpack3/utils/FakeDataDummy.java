package com.teguh.dicodingjetpack3.utils;


import com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity;

import static com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity.TYPE_MOVIE;
import static com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity.TYPE_TV_SHOW;

public class FakeDataDummy {

	public static ItemEntity getDummyMovie() {
		return new ItemEntity(
				508943,
				"/jTswp6KyDYKtvC52GbHagrZbGvD.jpg",
				"/620hnMVLu6RSZW6a5rwO8gqpt0t.jpg",
				"Luca",
				TYPE_MOVIE,
				"Animation, Comedy, Family, Fantasy",
				"Luca and his best friend Alberto experience an unforgettable summer on the Italian Riviera. But all the fun is threatened by a deeply-held secret: they are sea monsters from another world just below the water’s surface.",
				"EN",
				"Kamis, 17 Jun 2021",
				8.2f,
				1383,
				false
		);
	}

	public static ItemEntity getDummyTvShow() {
		return new ItemEntity(
				84958,
				"/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg",
				"/ykElAtsOBoArgI1A8ATVH0MNve0.jpg",
				"Loki",
				TYPE_TV_SHOW,
				"Drama, Sci-Fi & Fantasy",
				"After stealing the Tesseract during the events of “Avengers: Endgame,” an alternate version of Loki is brought to the mysterious Time Variance Authority, a bureaucratic organization that exists outside of time and space and monitors the timeline. They give Loki a choice: face being erased from existence due to being a “time variant”or help fix the timeline and stop a greater threat.",
				"EN",
				"Rabu, 9 Jun 2021",
				8.1f,
				4048,
				false
		);
	}
}
