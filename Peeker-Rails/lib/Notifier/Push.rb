# frozen_string_literal: true

require 'onesignal'
module Notifier
  # This allows sending Push notifications.
  module Push
    class << self
      API_USER_KEY = 'YzdhMjJjYjctMmFlYS00OTk0LWFmNGItZjgxZjBkZGJjMzAw'
      APP_KEY = 'MmYyNjNlNjgtNjk2Mi00ZjcwLThjM2MtMjZkZGQwYmE5ODA0'
      APP_ID = '07bca898-753e-4e19-ad43-3031231441a6'
      def configure
        # setup authorization
        OneSignal.configure do |config|
          # Configure Bearer authorization
          config.user_key = API_USER_KEY
          config.app_key = APP_KEY
        end
      end

      def send_push(user_id, title, subtitle, content, data)
        configure
        puts "Sending Push to #{user_id}"
        api_instance = OneSignal::DefaultApi.new
        notification = OneSignal::Notification.new(
          {
            app_id: APP_ID,
            include_external_user_ids: [user_id],
            headings: OneSignal::StringMap.new(en: title),
            subtitle: OneSignal::StringMap.new(en: subtitle),
            contents: OneSignal::StringMap.new(en: content),
            data:,
            is_chrome_web: false,
            is_any_web: false,
            is_ios: false,
            content_available: true,
            is_android: true,
            channel_for_external_user_ids: 'push'
          }
        )
        begin
          # Create notification
          data, status_code, _headers = api_instance.create_notification_with_http_info(notification)
          p status_code # => 2xx
          p data # => <InlineResponse200>
        rescue OneSignal::ApiError => e
          puts "Error when calling DefaultApi->create_notification: #{e}"
        rescue StandardError => e
          puts "Error when creating notification: #{e}"
        end
      end
    end
  end
end
