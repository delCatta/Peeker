# frozen_string_literal: true

json.extract! user, :id, :name, :last_name, :email, :notifications_enabled, :days_about_to_expire, :created_at, :updated_at
json.tags user.tags, :id, :name, :user_id, :updated_at, :created_at
