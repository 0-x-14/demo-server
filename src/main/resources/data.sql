-- User 테이블에 데이터 삽입
# INSERT INTO user (user_name, nickname, email, profile_image, user_status) VALUES
#     ('떡볶이매니아', '떡볶이매니아', 'ttokbokki@example.com', 'default.png', 1);

-- Course 테이블에 데이터 삽입 (유효한 user_id 사용)
# INSERT INTO course (course_name, detail, created_at, theme, distance, time, kcal, course_image, course_like) VALUES
#     ('봄에 걷기 좋은 벚꽃 코스', '봄에 걷기 좋은 벚꽃 코스 설명입니다.', NOW(), 1, 5.5, 120, 300, 'image_url', 0);
#
# -- Reply 테이블에 데이터 삽입
# INSERT INTO reply (reply_content, reply_time) VALUES
#     ('정말 멋진 코스네요!', NOW());
# INSERT INTO reply (reply_content, reply_time) VALUES
#     ('가족과 함께 가기 좋을 것 같아요.', NOW());


##다시

-- 더미 유저 데이터 삽입
INSERT INTO user (user_id, user_name, nickname, email, profile_image, user_status) VALUES
    (1234, '떡볶이매니아', '떡볶이매니아', 'ttokbokki@example.com', 'default.png', 1);

-- 더미 코스 데이터 삽입
INSERT INTO course (course_id, course_name, detail, created_at, theme, distance, time, kcal, course_image, course_like, user_id) VALUES
    (1, '봄에 걷기 좋은 벚꽃 코스', '봄에 걷기 좋은 벚꽃 코스 설명입니다.', NOW(), 1, 5.5, 120, 300, 'image_url', 0, 1234);

-- 더미 댓글 데이터 삽입
INSERT INTO reply (reply_content, reply_time, course_id, user_id) VALUES
    ('정말 멋진 코스네요!', NOW(), 1, 3460794882);
INSERT INTO reply (reply_content, reply_time, course_id, user_id) VALUES
    ('가족과 함께 가기 좋을 것 같아요.', NOW(), 1, 1234);


## 더미 데이터 다시 삽입
INSERT INTO user (user_id, user_name, nickname, email, profile_image, user_status) VALUES
    (11111, '따릉이좋아요', '따릉이좋아요', 'ddareunglike@example.com', 'default.png', 0);
INSERT INTO user (user_id, user_name, nickname, email, profile_image, user_status) VALUES
    (22222, '따릉잉_official', 'official', 'ddareungofficial@example.com', 'default.png', 0);
INSERT INTO user (user_id, user_name, nickname, email, profile_image, user_status) VALUES
    (33333, '따릉잉따릉잉', '따릉잉따릉잉', 'ddareungddareung@example.com', 'default.png', 0);


INSERT INTO course (course_id, course_name, detail, created_at, theme, distance, time, kcal, course_image, course_like, user_id) VALUES
    (2, '벚꽃 명소 양재천, 따릉이와 함께!', '벚꽃 맛집 양재천

그대여 그대여 그대여 그대여

오늘은 우리 같이 따릉이해요 양재천을~

사랑하는 사람과 함께, 양재천을 따라 가보는 거는 어떨까요? 아 아직 서로 알아가는 사이라구요? 양재천에서 따릉이와 함께, 벚꽃 구경을 한번 해봐요. 몽글몽글 설레는 감정이 저절로 생길겁니다. 제가 장담할게요.',
     NOW(), 1, 5.5, 120, 300, 'image_url', 0, 11111);
-- 더미 댓글 데이터 삽입
INSERT INTO reply (reply_content, reply_time, course_id, user_id) VALUES
    ('봄에 여자친구랑 벚꽃 어디서 볼까 고민중이었는데, 양재천 한번 가봐야겠어요!', NOW(), 2, 22222);


INSERT INTO reply (reply_content, reply_time, course_id, user_id) VALUES
    ('최근에 알아가고 있는 분과 이 글 보고 같이 양재천가서 따릉이 탔는데, 너무 좋았아요!! 여기 데이트 코스 강추!!!', NOW(), 2, 33333);

INSERT INTO spot (spot_type, spot_name, spot_lat, spot_lng, course_id) VALUES
                                                                            ('출발지', '출발지1', 37.5665, 126.9780, 1),
                                                                            ('경유지1', '경유지1-1', 37.5670, 126.9785, 1),
                                                                            ('경유지2', '경유지1-2', 37.5680, 126.9790, 1),
                                                                            ('도착지', '도착지1', 37.5690, 126.9800, 1);
