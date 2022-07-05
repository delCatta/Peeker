# frozen_string_literal: true

class Document < ApplicationRecord
  belongs_to :user
  has_and_belongs_to_many :tags
  scope :favorites, -> { where(favorite: true) }
  scope :about_to_expire, -> { where('expiration_date <= ?', (Current.user&.days_about_to_expire || 30).days.from_now) }
  has_one_attached :file

  after_update :send_about_to_expire_notification, if: :about_to_expire?
  after_update :send_expire_notification, if: :expired?

  def toggle_tag(tag)
    if tag.in? tags
      tags.delete(tag)
    else
      tags.push(tag)
    end
  end

  def about_to_expire?
    return false if expiration_date.blank?

    expiration_date <= user.days_about_to_expire.days.from_now
  end

  def expired?
    return false if expiration_date.blank?

    expiration_date <= Date.current
  end

  def send_about_to_expire_notification
    return unless user.notifications_enabled

    Notification.create(heading: 'Documento por Expirar',
                        content: "#{name.gsub("\n", '').titleize} está por expirar.", data: self, user:)
  end

  def send_expire_notification
    return unless user.notifications_enabled

    Notification.create(heading: 'Documento Expirado',
                        content: "#{name.gsub("\n", '').titleize} expiró.", data: self, user:)
  end
end
