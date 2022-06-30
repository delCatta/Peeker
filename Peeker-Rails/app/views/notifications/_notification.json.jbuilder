# frozen_string_literal: true

json.extract! notification, :id, :heading, :subtitle, :content, :data, :type, :user_id, :created_at, :updated_at
json.url notification_url(notification, format: :json)
